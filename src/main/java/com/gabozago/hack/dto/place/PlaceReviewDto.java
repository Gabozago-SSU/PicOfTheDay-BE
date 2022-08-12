package com.gabozago.hack.dto.place;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlaceReviewDto {

    private Long user_id;
    private String user_name;
    private Long rating;
    private LocalDateTime updated_at;
    private Long likeCnt;

    @Builder
    public PlaceReviewDto(Long user_id, String user_name, Long rating, LocalDateTime updated_at,
                          Long likeCnt){
        this.user_id = user_id;
        this.user_name = user_name;
        this.rating = rating;
        this.updated_at = updated_at;
        this.likeCnt = likeCnt;
    }
}
