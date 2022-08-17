package com.gabozago.hack.dto.review;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class ReviewPostDto {
    private Long userId;
    private Long placeId;
    private ArrayList<String> keywords;
    private String content;
    private BigDecimal rate;
    private String image;

    @Builder
    public ReviewPostDto(Long userId, Long placeId, ArrayList<String> keywords, String content, BigDecimal rate, String image){
        this.userId = userId;
        this.placeId = placeId;
        this.keywords = keywords;
        this.content = content;
        this.rate = rate;
        this.image = image;
    }

}
