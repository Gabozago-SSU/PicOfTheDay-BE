package com.gabozago.hack.controller;

import com.gabozago.hack.dto.review.ReviewDetailDto;
import com.gabozago.hack.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
