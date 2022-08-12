package com.gabozago.hack.dto.feed;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedReviewDto {

    private Long reviewId;
    private String image;
    private LocalDateTime createdAt;

    @Builder
    public FeedReviewDto(Long reviewId, String image, LocalDateTime createdAt){
        this.reviewId = reviewId;
        this.image = image;
        this.createdAt = createdAt;
    }
}
