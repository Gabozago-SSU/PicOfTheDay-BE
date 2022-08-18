package com.gabozago.hack.service;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.dto.user.UserNicknameDto;
import com.gabozago.hack.dto.user.UserProfileImageDto;
import com.gabozago.hack.repository.place.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepo userRepository;
    private final S3Uploader s3Uploader;

    public ResponseEntity setNickname(UserNicknameDto userNicknameDto){
        User user = userRepository.findById(userNicknameDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        user.setName(userNicknameDto.getNickname());
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity setProfileImage(UserProfileImageDto userProfileImageDto, MultipartFile multipartFile) throws IOException{

        User user = userRepository.findById(userProfileImageDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        String[] value = s3Uploader.upload(multipartFile, "user");
        user.setProfileImage(value[1]); // 이미지 url 저장?
        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }
}
