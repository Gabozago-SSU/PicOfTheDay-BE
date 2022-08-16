package com.gabozago.hack.dto.place;

import com.gabozago.hack.domain.place.PlaceImage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceDto {

    private String name;
    private String address;
    private Long rate;
    private String phoneNumber;
    private String category;
    
    private String content;
    private List<String> images = new ArrayList<>();

    @Builder
    public PlaceDto(String name, String address, Long rate, String phoneNumber,
                         String category, String content){
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.content = content;
    }


}
