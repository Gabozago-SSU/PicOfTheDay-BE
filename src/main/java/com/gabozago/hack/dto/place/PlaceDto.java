package com.gabozago.hack.dto.place;

import com.gabozago.hack.domain.place.PlaceImage;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceDto {

    private String name;
    private String address;
    private Long rate;
    private String phone_number;
    private String category;
    private List<PlaceImage> images = new ArrayList<>();

    @Builder
    public PlaceDto(String name, String address, Long rate, String phone_number,
                         String category, List<PlaceImage> images){
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.phone_number = phone_number;
        this.category = category;
        this.images = images;
    }
}
