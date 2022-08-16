package com.gabozago.hack.dto.place;

import lombok.Builder;
import lombok.Data;

@Data
public class PlaceSearchDto {

    private Long placeId;
    private String placeName;

    @Builder
    public PlaceSearchDto(Long placeId, String placeName){
        this.placeId = placeId;
        this.placeName = placeName;

    }
}
