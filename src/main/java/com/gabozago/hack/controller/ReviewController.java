package com.gabozago.hack.controller;

import com.gabozago.hack.dto.place.PlaceSearchDto;
import com.gabozago.hack.dto.review.*;
import com.gabozago.hack.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public ReviewDetailDto getReviewDetail(@RequestParam(name = "reviewId") Long id,
                                           @RequestParam(name = "userId") Long user_id) {
        return reviewService.getReviewDetail(id, user_id);
    }

    @PostMapping("/like")
    public ResponseEntity reviewLike(@RequestBody ReviewLikeDto reviewLikeDto){
        return reviewService.reviewLike(reviewLikeDto);
    }

    @DeleteMapping("/unlike")
    public ResponseEntity reviewUnLike(@RequestBody ReviewLikeDto reviewLikeDto){
        return reviewService.reviewUnLike(reviewLikeDto);
    }

    /**
     * 리뷰 업로드
     */
    @PostMapping("")
    public ResponseEntity postReview(@RequestPart(name = "reviewPost") ReviewPostDto reviewPostDto,
                                     @RequestPart(name = "image")MultipartFile[] multipartFile) throws IOException {
        return reviewService.postReview(reviewPostDto, multipartFile);
    }

    /**
     * 리뷰 삭제 요청
     */
    @DeleteMapping("")
    public ResponseEntity deleteReview(@RequestBody ReviewDeleteDto reviewDeleteDto){
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
