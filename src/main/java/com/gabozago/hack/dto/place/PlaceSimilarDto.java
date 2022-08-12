package com.gabozago.hack.dto.place;

import lombok.Builder;
import lombok.Data;

@Data
public class PlaceSimilarDto {
    private Long place_id;
    private String category;

    @Builder
    public PlaceSimilarDto(Long place_id, String category){
        this.place_id = place_id;
        this.category =category;
    }
}
