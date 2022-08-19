package com.gabozago.hack.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserReviewDto {
    private Long userId;
    private Long reviewId;
    private String reviewImage;

    @Builder
    public UserReviewDto(Long userId, Long reviewId, String reviewImage){
        this.userId = userId;
        this.reviewId = reviewId;
        this.reviewImage = reviewImage;
    }
}
