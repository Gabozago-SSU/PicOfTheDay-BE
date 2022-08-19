package com.gabozago.hack.dto.user;

import com.gabozago.hack.dto.review.ReviewLikeDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoDto {
    private Long userId;
    private String profileImage;
    private String name;
    private Integer mileage;

    private List<UserReviewDto> userReviews = new ArrayList<>();

    // 좋아요한 포토존 넘겨주기
    private List<ReviewLikeDto> userReviewLikes = new ArrayList<>();

    @Builder
    public UserInfoDto(Long userId, String profileImage, String name, Integer mileage){
        this.userId = userId;
        this.profileImage = profileImage;
        this.name = name;
        this.mileage = mileage;
    }

}
