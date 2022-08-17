package com.gabozago.hack.dto.feed;

import com.gabozago.hack.domain.review.ReviewImage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedPlaceSearchDto {

    private Long reviewId;
    private List<ReviewImage> image;
    private LocalDateTime createdAt;

    @Builder
    public FeedPlaceSearchDto(Long reviewId, List<ReviewImage> image, LocalDateTime createdAt){
        this.reviewId = reviewId;
        this.image = image;
        this.createdAt = createdAt;
    }
}
