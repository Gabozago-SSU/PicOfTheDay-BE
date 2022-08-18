package com.gabozago.hack.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserNicknameDto {
    private Long userId;
    private String nickname;

    @Builder
    public UserNicknameDto(Long userId, String nickname){
        this.userId = userId;
        this.nickname = nickname;
    }

}
