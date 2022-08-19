package com.gabozago.hack.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
public class ReviewLikeDto {
    private Long userId;
    private Long reviewId;

    @Builder
    public ReviewLikeDto(Long userId, Long reviewId){
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
