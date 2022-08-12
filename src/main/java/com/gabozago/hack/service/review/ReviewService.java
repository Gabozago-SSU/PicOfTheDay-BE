package com.gabozago.hack.service.review;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewLike;
import com.gabozago.hack.dto.review.ReviewDetailDto;
import com.gabozago.hack.repository.place.UserRepo;
import com.gabozago.hack.repository.review.ReviewLikeRepo;
import com.gabozago.hack.repository.review.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final ReviewLikeRepo reviewLikeRepo;
    private final UserRepo userRepo;

    /**
     * 리뷰 좋아요
     * @param user_id
     * @param review_id
     * @return ok
     */
    public ResponseEntity reviewLike(Long user_id, Long review_id) {
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        Review review = reviewRepo.findById(review_id)
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없음"));
        ReviewLike reviewLike = new ReviewLike();

        reviewLike.setReviewLike(review, user);
        reviewLikeRepo.save(reviewLike);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 리뷰 디테일
     */
    public ReviewDetailDto getReviewDetail(Long reviewId) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없음"));
        ReviewDetailDto reviewDetailDto = ReviewDetailDto.builder()
                .userId(review.getUser().getId())
                .placeId(review.getPlace().getId())
                .address(review.getPlace().getAddress())
                .userName(review.getUser().getName())
                .build();

        return reviewDetailDto;
    }
}
