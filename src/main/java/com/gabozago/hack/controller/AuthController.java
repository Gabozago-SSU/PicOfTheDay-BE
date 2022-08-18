package com.gabozago.hack.controller;

import com.gabozago.hack.dto.user.UserNicknameDto;
import com.gabozago.hack.dto.user.UserProfileImageDto;
import com.gabozago.hack.service.AuthService;
import com.gabozago.hack.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private AuthService authService;



    /**
     * 카카오 로그인 요청
     */
    @GetMapping("/kakao")
    public void kakaoLogin(HttpServletResponse httpServletResponse) throws IOException{
        httpServletResponse.sendRedirect("https://kauth.kakao.com/oauth/authorize?client_id=f59f1da1323e0e466c18bfdf8d2c67b2&redirect_uri=http://13.125.213.188/auth/kakao/callback&response_type=code");
    }

    /**
     * 카카오 로그인 이후 콜백
     */
    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam String code, Model model) throws IOException {
        String access_token = kakaoService.getToken(code);
        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);
        kakaoService.kakaoSignup(userInfo);
        model.addAttribute("userId", userInfo.get("id"));

        return "리다이렉트할 주소"; // 프론트에서 닉네임 설정하는 곳으로 보내기
    }

    /**
     * 구글 콜백
     */
    @GetMapping("/google/callback")
    @ResponseBody
    public String googleCallback(@RequestParam(name="code") String code, Model model) throws IOException{
        Long userId = authService.getUserIdByCode(code);
        model.addAttribute("userId", userId);

        return "리다이렉트할 주소";
    }

    /**
     * 로그아웃 - logout redirect url은 홈으로 설정해놓음. 바꿀거면 말해주기. 카카오 developments 사이트에서 바꿔야함
     * 로그아웃 기능을 만들지 않은 것 같아 구글 로그아웃은 따로 구현 안함
     */
    @GetMapping("/logout")
    public void logout(HttpServletResponse httpServletResponse) throws IOException{
        //카카오 로그아웃
        httpServletResponse.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id=f59f1da1323e0e466c18bfdf8d2c67b2&logout_redirect_uri=http://13.125.213.188/");
    }

    /**
     * 닉네임 수정 요청
     */
    @PostMapping("/nickname")
    public ResponseEntity setNickname(@RequestBody UserNicknameDto userNicknameDto){
        return authService.setNickname(userNicknameDto);
    }

    /**
     * 프로필 사진 수정 요청
     */
    @PostMapping("/profileimage")
    public ResponseEntity setProfileImage(@RequestPart(name="profileImagePost") UserProfileImageDto userProfileImageDto,
                                          @RequestPart(name="image")MultipartFile multipartFile) throws IOException{
        return authService.setProfileImage(userProfileImageDto, multipartFile);
    }


}
