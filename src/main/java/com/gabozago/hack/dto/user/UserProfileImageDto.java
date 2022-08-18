package com.gabozago.hack.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserProfileImageDto {
    private Long userId;
    private String profileImage;

    @Builder
    public UserProfileImageDto(Long userId, String profileImage){
        this.userId = userId;
        this.profileImage = profileImage;
    }
}
