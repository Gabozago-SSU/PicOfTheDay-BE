package com.gabozago.hack.dto;

import com.gabozago.hack.domain.place.PlaceKeyword;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class KeywordDto {

    private String keyword;
    private Long placeId;
    private String placeName;
    private BigDecimal rate;
    private String category;
    private List<PlaceKeyword> keywordList = new ArrayList<>();

    public KeywordDto(String keyword){
        this.keyword = keyword;
    }

    @Builder
    public KeywordDto (String keyword, Long placeId, String placeName,
                       BigDecimal rate, String category){
        this.keyword = keyword;
        this.placeId = placeId;
        this.placeName = placeName;
        this.rate = rate;
        this.category = category;
    }
}
