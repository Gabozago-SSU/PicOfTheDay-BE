package com.gabozago.hack.config.auth;

import com.gabozago.hack.config.auth.dto.OAuthAttributes;
import com.gabozago.hack.config.auth.dto.SessionUser;
import com.gabozago.hack.domain.User;
import com.gabozago.hack.repository.place.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepo userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());


        signupIfNotExist(attributes);
        User user = userRepository.findBySnsId(attributes.getSnsId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));;

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("user")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private void signupIfNotExist(OAuthAttributes attributes) {
        if(userRepository.findBySnsId(attributes.getSnsId()).equals(Optional.empty())){
            userRepository.save(attributes.toEntity());
        }
    }

}
