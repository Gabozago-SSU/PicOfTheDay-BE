package com.gabozago.hack.controller;

import com.gabozago.hack.dto.user.UserInfoDto;
import com.gabozago.hack.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    /**
     * 다른 유저 페이지
     */
    @GetMapping("/others")
    public UserInfoDto getOthersUserPage(@RequestParam(name="userId") String userId){
        return mypageService.getOthersUserPage(userId);
    }

    /**
     * 마이페이지
     */
    @PostMapping("/info")
    public UserInfoDto getMyPage(@RequestBody Map<String, String> map){
        return mypageService.getMyPage(map.get("userId"));
    }
}
