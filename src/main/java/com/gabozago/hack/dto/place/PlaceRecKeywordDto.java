package com.gabozago.hack.dto.place;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceRecKeywordDto {
    private List<String> keywords = new ArrayList<>();
    private List<PlaceSimilarDto> similarDtos = new ArrayList<>();
}
