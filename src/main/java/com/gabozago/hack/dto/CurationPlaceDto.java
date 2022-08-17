package com.gabozago.hack.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class CurationPlaceDto {

    private Long placeId;
    private String image;
    private String category;
    private BigDecimal rate;
    private String title;
    private Long reviewId;

    @Builder
    public CurationPlaceDto (Long placeId, String image, String category, BigDecimal rate,
                             String title, Long reviewId){
        this.placeId = placeId;
        this.image = image;
        this.category = category;
        this.rate = rate;
        this.reviewId = reviewId;
        this.title = title;
    }
}
