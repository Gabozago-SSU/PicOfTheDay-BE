package com.gabozago.hack.service.review;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewLike;
import com.gabozago.hack.dto.place.PlaceSearchDto;
import com.gabozago.hack.dto.review.*;
import com.gabozago.hack.repository.place.PlaceRepo;
import com.gabozago.hack.repository.place.UserRepo;
import com.gabozago.hack.repository.review.ReviewLikeRepo;
import com.gabozago.hack.repository.review.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final ReviewLikeRepo reviewLikeRepo;
    private final UserRepo userRepo;
    private final PlaceRepo placeRepo;

    /**
     * 리뷰 좋아요
     */
    public ResponseEntity reviewLike(ReviewLikeDto reviewLikeDto) {
        User user = userRepo.findById(reviewLikeDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        Review review = reviewRepo.findById(reviewLikeDto.getReviewId())
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없음"));
        ReviewLike reviewLike = new ReviewLike();

        reviewLike.setReviewLike(review, user);
        reviewLikeRepo.save(reviewLike);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 리뷰 디테일
     */
    public ReviewDetailDto getReviewDetail(Long reviewId, Long userId) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없음"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        ReviewLike reviewLike = reviewLikeRepo.findByUserAndReview(user,review)
                .orElseGet(() -> new ReviewLike());
        ReviewDetailDto reviewDetailDto;
        if(reviewLike.getId() != null) {
            reviewDetailDto = ReviewDetailDto.builder()
                    .userId(review.getUser().getId())
                    .profile(review.getUser().getProfileImage())
                    .placeId(review.getPlace().getId())
                    .address(review.getPlace().getAddress())
                    .userName(review.getUser().getName())
                    .reviewLikeCnt(review.getReviewLikes().size())
                    .isLike(true)
                    .build();
        } else {
            reviewDetailDto = ReviewDetailDto.builder()
                    .userId(review.getUser().getId())
                    .profile(review.getUser().getProfileImage())
                    .placeId(review.getPlace().getId())
                    .address(review.getPlace().getAddress())
                    .userName(review.getUser().getName())
                    .reviewLikeCnt(review.getReviewLikes().size())
                    .isLike(false)
                    .build();
        }

        return reviewDetailDto;
    }

    /**
     * 리뷰 저장
     */
    public ResponseEntity postReview(ReviewPostDto reviewPostDto){
        Place place = placeRepo.findById(reviewPostDto.getPlaceId())
                .orElseThrow(() -> new IllegalStateException("그런 장소 없음"));

        User user = userRepo.findById(reviewPostDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        Review review = new Review();
        review.setUser(user);
        review.setImage(reviewPostDto.getImage());
        review.setContent(reviewPostDto.getContent());
        review.setRate(reviewPostDto.getRate());
        review.setPlace(place);


        reviewRepo.save(review);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 리뷰 삭제
     */
    public Long deleteReview(ReviewDeleteDto reviewDeleteDto){
        reviewRepo.deleteById(reviewDeleteDto.getReviewId());
        return reviewDeleteDto.getReviewId();
    }

    /**
     * placename으로 해당 장소가 등록되어있는지 검색
     */
    public PlaceSearchDto getReviewByPlaceName(String placeName){
        Place place = placeRepo.findByName(placeName)
                .orElse(null);

        if(place == null){
            return PlaceSearchDto.builder().placeName(null).build();
        }

        PlaceSearchDto placeSearchDto = PlaceSearchDto.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .build();

        return placeSearchDto;
    }

    public ResponseEntity addPlace(ReviewLocationAddDto reviewLocationAddDto){

        Place place = new Place();
        place.setName(reviewLocationAddDto.getPlaceName());

        placeRepo.save(place);
        return new ResponseEntity(HttpStatus.OK);
    }


    public ResponseEntity reviewUnLike(ReviewLikeDto reviewLikeDto) {
        User user = userRepo.findById(reviewLikeDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        Review review = reviewRepo.findById(reviewLikeDto.getReviewId())
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없음"));
        ReviewLike reviewLike = reviewLikeRepo.findByUserAndReview(user, review)
                .orElseThrow(() -> new IllegalStateException("그런 좋아요 없음"));

        review.setLikeCnt(review.getLikeCnt() - 1);
        reviewLikeRepo.deleteById(reviewLike.getId());
        return new ResponseEntity("성공", HttpStatus.OK);
    }
}
