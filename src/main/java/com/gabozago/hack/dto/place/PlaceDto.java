package com.gabozago.hack.dto.place;

import com.gabozago.hack.domain.place.PlaceImage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceDto {

    private String name;
    private String address;
    private BigDecimal rate;
    private String phoneNumber;
    private String category;

    private boolean isLike;
    private String content;
    private List<String> images = new ArrayList<>();
    private List<String> keywords = new ArrayList<>();

    @Builder
    public PlaceDto(String name, String address, BigDecimal rate, String phoneNumber,
                         String category, String content, boolean isLike){
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.content = content;
        this.isLike = isLike;
    }


}
