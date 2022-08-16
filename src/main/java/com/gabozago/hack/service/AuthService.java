package com.gabozago.hack.service;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.dto.UserDto;
import com.gabozago.hack.repository.place.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepository;

    public ResponseEntity setNickname(UserDto userDto){
        User user = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        user.setName(userDto.getNickname());
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
