package com.gabozago.hack.dto.place;

import lombok.Builder;
import lombok.Data;

@Data
public class PlaceKeywordDto {

    private Long keywordId;
    private String keyword;

    @Builder
    public PlaceKeywordDto(Long keywordId, String keyword){
        this.keyword = keyword;
        this.keywordId = keywordId;
    }
}
