package com.gabozago.hack.dto.place;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PlaceSimilarDto {
    private Long place_id;
    private String category;
    private String title;
    private String image;
    private BigDecimal rate;

    public PlaceSimilarDto(){

    }
    @Builder
    public PlaceSimilarDto(Long place_id, String category, String title,
                           String image, BigDecimal rate){
        this.place_id = place_id;
        this.category =category;
        this.title = title;
        this.image = image;
        this.rate = rate;
    }
}
