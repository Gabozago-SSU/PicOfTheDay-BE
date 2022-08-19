package com.gabozago.hack.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
public class ReviewLikeDto {
    private Long userId;
    private Long reviewId;

    private String image;

    @Builder
    public ReviewLikeDto(Long userId, Long reviewId, String image){
        this.userId = userId;
        this.reviewId = reviewId;
        this.image = image;
    }
}
