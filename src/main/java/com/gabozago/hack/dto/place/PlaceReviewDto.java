package com.gabozago.hack.dto.place;

import com.gabozago.hack.dto.review.ReviewKeywordDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceReviewDto {

    private Long userId;
    private String userName;
    private Long rating;
    private LocalDateTime createdAt;
    private Integer likeCnt;

    private String content;

    private Long reviewId;

    private String profile;

    private boolean isLike;

    private List<ReviewKeywordDto> keywords = new ArrayList<>();
    @Builder
    public PlaceReviewDto(Long userId, String userName, Long rating, LocalDateTime createdAt,
                          Integer likeCnt, String content, Long reviewId, String profile,
                          boolean isLike){
        this.userId = userId;
        this.isLike = isLike;
        this.userName = userName;
        this.rating = rating;
        this.createdAt = createdAt;
        this.likeCnt = likeCnt;
        this.content = content;
        this.reviewId = reviewId;
        this.profile = profile;
    }


}
