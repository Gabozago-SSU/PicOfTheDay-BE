package com.gabozago.hack.service.place;


import com.gabozago.hack.domain.Keyword;
import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.place.*;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewLike;
import com.gabozago.hack.dto.place.PlaceDto;
import com.gabozago.hack.dto.place.PlaceLikeDto;
import com.gabozago.hack.dto.place.PlaceReviewDto;
import com.gabozago.hack.dto.place.PlaceSimilarDto;
import com.gabozago.hack.dto.review.ReviewKeywordDto;
import com.gabozago.hack.repository.place.*;
import com.gabozago.hack.repository.review.ReviewLikeRepo;
import com.gabozago.hack.repository.review.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PlaceService {

    private final ReviewLikeRepo reviewLikeRepo;
    private final PlaceRepo placeRepo;
    private final PlaceLikeRepo placeLikeRepo;
    private final UserRepo userRepo;
    private final ReviewRepo reviewRepo;
    private final PlaceImageRepo placeImageRepo;
    private final PlaceConKeywordRepo placeConKeywordRepo;
    /**
     * 장소 디테일
     */
    public PlaceDto getPlace(Long place_id, Long user_id){
        Place place = placeRepo.findById(place_id)
                .orElseThrow(() -> new IllegalStateException("그런 장소 없음"));
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        List<PlaceImage> images = placeImageRepo.findByPlace(place);
        List<PlaceConKeyword> placeConKeywords = placeConKeywordRepo.findByPlace(place);
        PlaceLike placeLike = placeLikeRepo.findByPlaceAndUser(place, user)
                .orElseGet(() -> new PlaceLike(null));

        PlaceDto placeDto;

        if(placeLike.getId() == null){
            placeDto = PlaceDto.builder()
                    .name(place.getName())
                    .address(place.getAddress())
                    .rate(place.getRate())
                    .phoneNumber(place.getPhoneNumber())
                    .category(place.getCategory())
                    .content(place.getContent())
                    .isLike(false)
                    .build();
        } else {
            placeDto = PlaceDto.builder()
                    .name(place.getName())
                    .address(place.getAddress())
                    .rate(place.getRate())
                    .phoneNumber(place.getPhoneNumber())
                    .category(place.getCategory())
                    .content(place.getContent())
                    .isLike(true)
                    .build();
        }


        for (PlaceImage image : images) {
            placeDto.getImages().add(image.getImage());
        }

        for(PlaceConKeyword placeConKeyword : placeConKeywords) {
            placeDto.getKeywords().add(placeConKeyword.getPlaceKeyword().getKeyword());
        }

        return placeDto;
    }

    /**
     * 좋아요
     */
    public ResponseEntity likePlace(PlaceLikeDto placeLikeDto){
        PlaceLike placeLike = new PlaceLike();
        Place place = placeRepo.findById(placeLikeDto.getPlaceId())
                .orElseThrow(()-> new IllegalStateException("그런 장소 없음"));
        User user = userRepo.findById(placeLikeDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        placeLike.setPlaceLike(place, user);
        placeLikeRepo.save(placeLike);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 좋아요 취소
     */
    public ResponseEntity unlikePlace(PlaceLikeDto placeLikeDto){
        Place place = placeRepo.findById(placeLikeDto.getPlaceId())
                .orElseThrow(() -> new IllegalStateException("그런 장소 없음"));
        User user = userRepo.findById(placeLikeDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        PlaceLike placeLike = placeLikeRepo.findByPlaceAndUser(place, user)
                .orElseThrow(()-> new IllegalStateException("그런 좋아요 없음"));



        placeLikeRepo.deleteById(placeLike.getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 인기 게시물 가져오기
     */
    public List<PlaceReviewDto> getPopularReview(Long place_id, Long user_id){
        Place place = placeRepo.findById(place_id)
                .orElseThrow(() -> new IllegalStateException("그런 장소 없음"));
        List<Review> reviews = reviewRepo.findByPlaceOrderByLikeCntDesc(place)
                .orElseThrow(()-> new IllegalStateException("그런 리뷰 없음"));
        List<PlaceReviewDto> reviewDtos = new ArrayList<>();
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));


        for (Review review : reviews){
            ReviewLike reviewLike = reviewLikeRepo.findByUserAndReview(user, review)
                    .orElseGet(() -> new ReviewLike());
            PlaceReviewDto reviewDto;
            if(reviewLike.getId() != null){
                reviewDto = PlaceReviewDto.builder()
                        .userId(review.getUser().getId())
                        .userName(review.getUser().getName())
                        .createdAt(review.getCreatedAt())
                        .likeCnt(review.getReviewLikes().size())
                        .content(review.getContent())
                        .reviewId(review.getId())
                        .profile(review.getUser().getProfileImage())
                        .isLike(true)
                        .build();
            } else {
                reviewDto = PlaceReviewDto.builder()
                        .userId(review.getUser().getId())
                        .userName(review.getUser().getName())
                        .createdAt(review.getCreatedAt())
                        .likeCnt(review.getReviewLikes().size())
                        .content(review.getContent())
                        .reviewId(review.getId())
                        .profile(review.getUser().getProfileImage())
                        .isLike(false)
                        .build();
            }

            for(Keyword keyword : review.getKeywords()){
                ReviewKeywordDto reviewKeywordDto = ReviewKeywordDto.builder()
                        .name(keyword.getName())
                        .build();

                reviewDto.getKeywords().add(reviewKeywordDto);
            }


            reviewDtos.add(reviewDto);
        }

        return reviewDtos;
    }

    /**
     * 최근 게시물 가져오기
     */
    public List<PlaceReviewDto> getRecentReview(Long place_id, Long user_id) {
        Place place = placeRepo.findById(place_id)
                .orElseThrow(() -> new IllegalStateException("그런 장소 없음"));
        List<Review> reviews = reviewRepo.findByPlaceOrderByCreatedAtDesc(place)
                .orElseThrow(()-> new IllegalStateException("그런 리뷰 없음"));
        List<PlaceReviewDto> reviewDtos = new ArrayList<>();
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        for (Review review : reviews){
            ReviewLike reviewLike = reviewLikeRepo.findByUserAndReview(user, review)
                    .orElseGet(() -> new ReviewLike());
            PlaceReviewDto reviewDto;
            if(reviewLike.getId() != null){
                reviewDto = PlaceReviewDto.builder()
                        .userId(review.getUser().getId())
                        .userName(review.getUser().getName())
                        .createdAt(review.getCreatedAt())
                        .likeCnt(review.getReviewLikes().size())
                        .content(review.getContent())
                        .reviewId(review.getId())
                        .profile(review.getUser().getProfileImage())
                        .isLike(true)
                        .build();
            } else {
                reviewDto = PlaceReviewDto.builder()
                        .userId(review.getUser().getId())
                        .userName(review.getUser().getName())
                        .createdAt(review.getCreatedAt())
                        .likeCnt(review.getReviewLikes().size())
                        .content(review.getContent())
                        .reviewId(review.getId())
                        .profile(review.getUser().getProfileImage())
                        .isLike(false)
                        .build();
            }

            for(Keyword keyword : review.getKeywords()){
                ReviewKeywordDto reviewKeywordDto = ReviewKeywordDto.builder()
                        .name(keyword.getName())
                        .build();

                reviewDto.getKeywords().add(reviewKeywordDto);
            }

            reviewDtos.add(reviewDto);
        }

        return reviewDtos;
    }

    /**
     * 비슷한 게시물 출력
     */
    public List<PlaceSimilarDto> getSimilarPlace(String category) {
        List<Place> places = placeRepo.findByCategory(category)
                .orElseThrow(()-> new IllegalStateException("없는 카테 고리"));
        List<PlaceSimilarDto> placeSimilarDtos = new ArrayList<>();

        for(Place place : places){
            PlaceSimilarDto placeSimilarDto = PlaceSimilarDto.builder()
                    .place_id(place.getId())
                    .category(place.getCategory())
                    .image(place.getImages().get(0).getImage())
                    .title(place.getName())
                    .rate(place.getRate())
                    .build();

            placeSimilarDtos.add(placeSimilarDto);
        }

        return placeSimilarDtos;
    }
}
