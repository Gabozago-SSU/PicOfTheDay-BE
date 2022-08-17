package com.gabozago.hack.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
public class ReviewDetailDto {
    private Long userId;

    private String profile;
    private Long placeId;
    private String userName;
    private Long rate;
    private String address;

    private String image;

    private String content;

    private boolean isLike;

    private Integer reviewLikeCnt;
    @Builder
    public ReviewDetailDto(Long userId, Long placeId , String userName, Long rate,
                           String address, String image, String content, String profile,
                           boolean isLike, Integer reviewLikeCnt){
        this.userId = userId;
        this.reviewLikeCnt = reviewLikeCnt;
        this.isLike = isLike;
        this.profile = profile;
        this.placeId = placeId;
        this.userName = userName;
        this.rate = rate;
        this.address = address;
        this.image = image;
        this.content = content;
    }
}
