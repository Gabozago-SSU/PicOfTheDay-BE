package com.gabozago.hack.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CurationPlaceDto {

    private Long placeId;
    private String image;
    private String category;
    private Long rate;
    private Long curationId;

    @Builder
    public CurationPlaceDto (Long placeId, String image, String category, Long rate, Long curationId){
        this.placeId = placeId;
        this.image = image;
        this.category = category;
        this.rate = rate;
        this.curationId = curationId;
    }
}
