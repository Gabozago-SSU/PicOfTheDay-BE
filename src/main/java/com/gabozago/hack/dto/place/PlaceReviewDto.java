package com.gabozago.hack.dto.place;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlaceReviewDto {

    private Long userId;
    private String userName;
    private Long rating;
    private LocalDateTime createdAt;
    private Integer likeCnt;

    private String content;

    @Builder
    public PlaceReviewDto(Long userId, String userName, Long rating, LocalDateTime createdAt,
                          Integer likeCnt, String content){
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.createdAt = createdAt;
        this.likeCnt = likeCnt;
        this.content = content;
    }
}
