package com.gabozago.hack.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
public class ReviewDetailDto {
    private Long userId;
    private Long placeId;
    private String userName;
    private Long rate;
    private String address;

    @Builder
    public ReviewDetailDto(Long userId, Long placeId , String userName, Long rate,
                           String address){
        this.userId = userId;
        this.placeId = placeId;
        this.userName = userName;
        this.rate = rate;
        this.address = address;
    }
}
