package com.gabozago.hack.controller;

import com.gabozago.hack.dto.place.PlaceSearchDto;
import com.gabozago.hack.dto.review.ReviewDeleteDto;
import com.gabozago.hack.dto.review.ReviewDetailDto;
import com.gabozago.hack.dto.review.ReviewLocationAddDto;
import com.gabozago.hack.dto.review.ReviewPostDto;
import com.gabozago.hack.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public ReviewDetailDto getReviewDetail(@RequestParam(name = "reviewId") Long id) {
        return reviewService.getReviewDetail(id);
    }

    @GetMapping("/like")
    public ResponseEntity reviewLike(@RequestParam(name = "userId") Long user_id,
                                     @RequestParam(name = "reviewId") Long review_id){
        return reviewService.reviewLike(user_id, review_id);
    }

    /**
     * 리뷰 업로드
     */
    @PostMapping("")
    public ResponseEntity postReview(@RequestBody ReviewPostDto reviewPostDto){
        return reviewService.postReview(reviewPostDto);
    }

    /**
     * 리뷰 삭제 요청
     */
    @DeleteMapping("")
    public Long deleteReview(@RequestBody ReviewDeleteDto reviewDeleteDto){
        return reviewService.deleteReview(reviewDeleteDto);
    }

    /**
     * 리뷰 작성 시 위치 존재 여부, 없으면 null 리턴
     */
    @GetMapping("/search")
    public PlaceSearchDto getReviewByPlaceName(@RequestParam(name="placename") String placeName){
        return reviewService.getReviewByPlaceName(placeName);
    }


    /**
     * 위치 추가 요청
     */
    @PostMapping("/location")
    public ResponseEntity addPlace(@RequestBody ReviewLocationAddDto reviewLocationAddDto){
        return reviewService.addPlace(reviewLocationAddDto);
    }

    /**
     * 리뷰 작성 시 키워드 추가
     */

    /**
     * 이미지 리뷰테이블에 저장
     */


}
