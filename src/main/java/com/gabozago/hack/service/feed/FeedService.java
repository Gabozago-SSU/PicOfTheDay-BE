package com.gabozago.hack.service.feed;

import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewImage;
import com.gabozago.hack.dto.feed.FeedPlaceSearchDto;
import com.gabozago.hack.dto.place.PlaceSearchDto;
import com.gabozago.hack.repository.place.PlaceRepo;
import com.gabozago.hack.repository.review.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FeedService {

    private final ReviewRepo reviewRepo;
    private final PlaceRepo placeRepo;

    /**
     * 인기 게시물 받아오기
     */
    public List<FeedPlaceSearchDto> getPopularReview(){
        List<Review> reviews = reviewRepo.findAll(Sort.by(Sort.Direction.DESC,"likeCnt"));
        List<FeedPlaceSearchDto> feedPlaceSearchDtos = new ArrayList<>();

        for (Review review : reviews){
            FeedPlaceSearchDto feedPlaceSearchDto = FeedPlaceSearchDto.builder()
                    .reviewId(review.getId())
                    .createdAt(review.getCreatedAt())
                    .build();

            for(ReviewImage reviewImage : review.getImages()){
                feedPlaceSearchDto.getImage().add(reviewImage.getImage());
            }

            feedPlaceSearchDtos.add(feedPlaceSearchDto);
        }

        return feedPlaceSearchDtos;
    }

    /**
     * 최신 게시물 받아오기
     */
    public List<FeedPlaceSearchDto> getRecentReview(){
        List<Review> reviews = reviewRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<FeedPlaceSearchDto> feedPlaceSearchDtos = new ArrayList<>();
        for (Review review : reviews){
            FeedPlaceSearchDto feedPlaceSearchDto = FeedPlaceSearchDto.builder()
                    .reviewId(review.getId())
                    .createdAt(review.getCreatedAt())
                    .build();


            for(ReviewImage reviewImage : review.getImages()){
                feedPlaceSearchDto.getImage().add(reviewImage.getImage());
            }

            feedPlaceSearchDtos.add(feedPlaceSearchDto);
        }

        return feedPlaceSearchDtos;
    }

    /**
     * 검색 피드 리뷰만 가져오기
     */
    public List<PlaceSearchDto> getSearchFeedReview(String placeName){
        List<Place> places = placeRepo.findByNameContaining(placeName)
                .orElseThrow(() -> new IllegalStateException("그런 장소 없음"));

        List<PlaceSearchDto> placeSearchDtos = new ArrayList<>();

        for(Place place : places){
            PlaceSearchDto placeSearchDto = PlaceSearchDto.builder()
                    .placeId(place.getId())
                    .placeName(place.getName())
                    .build();

            placeSearchDtos.add(placeSearchDto);
        }

        return placeSearchDtos;
    }

}
