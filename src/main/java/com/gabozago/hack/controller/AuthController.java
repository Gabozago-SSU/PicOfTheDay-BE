package com.gabozago.hack.controller;

import com.gabozago.hack.dto.user.UserNicknameDto;
import com.gabozago.hack.dto.user.UserProfileImageDto;
import com.gabozago.hack.service.AuthService;
import com.gabozago.hack.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
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
//    @GetMapping("/kakao")
//    public void kakaoLogin(HttpServletResponse httpServletResponse) throws IOException{
//        httpServletResponse.sendRedirect("https://kauth.kakao.com/oauth/authorize?client_id=f59f1da1323e0e466c18bfdf8d2c67b2&redirect_uri=https://port-0-picoftheday-be-5cw30n24l6yuwga9.gksl1.cloudtype.app/auth/kakao/callback&response_type=code");
//    }

    /**
     * 카카오 로그인 이후 콜백
     */
    @PostMapping("/kakao")
    @ResponseBody
    public String kakaoCallback(@RequestBody Map<String, String> code) throws IOException {

        String access_token = kakaoService.getToken(code.get("code"));
        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);
        Boolean isRegistered = kakaoService.kakaoSignup(userInfo);

        Long id = kakaoService.getIdBySnsId((String) userInfo.get("id"));

        JSONObject json = new JSONObject();
        json.put("isRegistered", isRegistered);
        json.put("userId", userInfo.get("id"));

        return json.toString();
    }

    /**
     * 구글 콜백
     */
    @GetMapping("/google/callback")
    @ResponseBody
    public String googleCallback(@RequestParam(name="code") String code, Model model) throws IOException{
        Long userId = authService.getUserIdByCode(code);
        JSONObject json = new JSONObject();
        json.put("userId", userId);

        return json.toString();
    }

    /**
     * 로그아웃 - logout redirect url은 홈으로 설정해놓음. 바꿀거면 말해주기. 카카오 developments 사이트에서 바꿔야함
     *
     */
    @GetMapping("/logout")
    public void logout(@RequestParam(name="userId") String userId, HttpServletResponse httpServletResponse) throws IOException{
        String logoutUrl = authService.logout(userId);

        httpServletResponse.sendRedirect(logoutUrl);
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
