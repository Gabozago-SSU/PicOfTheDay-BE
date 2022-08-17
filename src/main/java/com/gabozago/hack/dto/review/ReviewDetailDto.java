package com.gabozago.hack.dto.review;

import com.gabozago.hack.domain.review.ReviewImage;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewDetailDto {
    private Long userId;

    private String profile;
    private Long placeId;
    private String userName;
    @Column(precision = 2, scale = 1)
    private BigDecimal rate;
    private String address;

    private List<String> image = new ArrayList<>();

    private String content;

    private boolean isLike;

    private Integer reviewLikeCnt;

    private  List<ReviewKeywordDto> keywords = new ArrayList<>();
    @Builder
    public ReviewDetailDto(Long userId, Long placeId , String userName, BigDecimal rate,
                           String address, List<ReviewImage> image, String content, String profile,
                           boolean isLike, Integer reviewLikeCnt){
        this.userId = userId;
        this.reviewLikeCnt = reviewLikeCnt;
        this.isLike = isLike;
        this.profile = profile;
        this.placeId = placeId;
        this.userName = userName;
        this.rate = rate;
        this.address = address;
        this.content = content;
    }
}
