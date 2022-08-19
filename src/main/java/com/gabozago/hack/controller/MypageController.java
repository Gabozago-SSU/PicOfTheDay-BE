package com.gabozago.hack.controller;

import com.gabozago.hack.dto.user.UserInfoDto;
import com.gabozago.hack.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    /**
     * 다른 유저 페이지
     */
    @GetMapping("others")
    public UserInfoDto getOthersUserPage(@RequestParam(name="userId") String userId){
        return mypageService.getOthersUserPage(userId);
    }
}
