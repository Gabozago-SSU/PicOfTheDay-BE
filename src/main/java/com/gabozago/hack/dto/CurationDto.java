package com.gabozago.hack.dto;

import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.dto.place.PlaceDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CurationDto {

    //curation id
    private Long id;
    private String subtitle;
    private List<CurationPlaceDto> places = new ArrayList<>();

    @Builder
    public CurationDto (Long id, String subtitle) {
        this.id = id;
        this.subtitle = subtitle;
    }
}
