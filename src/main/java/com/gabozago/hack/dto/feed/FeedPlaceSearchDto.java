package com.gabozago.hack.dto.feed;

import com.gabozago.hack.domain.review.ReviewImage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FeedPlaceSearchDto {

    private Long reviewId;
    private List<ReviewImage> image = new ArrayList<>();
    private LocalDateTime createdAt;

    @Builder
    public FeedPlaceSearchDto(Long reviewId, LocalDateTime createdAt){
        this.reviewId = reviewId;
        this.createdAt = createdAt;
    }
}
