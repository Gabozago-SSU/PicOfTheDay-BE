package com.gabozago.hack.service.feed;

import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.dto.feed.FeedReviewDto;
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
    public List<FeedReviewDto> getPopularReview(){
        List<Review> reviews = reviewRepo.findAll(Sort.by(Sort.Direction.DESC,"likeCnt"));
        List<FeedReviewDto> feedReviewDtos = new ArrayList<>();

        for (Review review : reviews){
            FeedReviewDto feedReviewDto = FeedReviewDto.builder()
                    .reviewId(review.getId())
                    .image(review.getImage())
                    .createdAt(review.getCreatedAt())
                    .build();

            feedReviewDtos.add(feedReviewDto);
        }

        return feedReviewDtos;
    }

    /**
     * 최신 게시물 받아오기
     */
    public List<FeedReviewDto> getRecentReview(){
        List<Review> reviews = reviewRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<FeedReviewDto> feedReviewDtos = new ArrayList<>();
        for (Review review : reviews){
            FeedReviewDto feedReviewDto = FeedReviewDto.builder()
                    .reviewId(review.getId())
                    .image(review.getImage())
                    .createdAt(review.getCreatedAt())
                    .build();

            feedReviewDtos.add(feedReviewDto);
        }

        return feedReviewDtos;
    }

    /**
     * 검색 피드 리뷰만 가져오기
     */
    public List<FeedReviewDto> getSearchFeedReview(String placeName){
        Place place = placeRepo.findByName(placeName)
                .orElseThrow(() -> new IllegalStateException("그런 장소 없음"));
        List<Review> reviews = reviewRepo.findByPlace(place)
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없음"));
        List<FeedReviewDto> reviewDtos = new ArrayList<>();

        for(Review review : reviews){
            FeedReviewDto reviewDto = FeedReviewDto.builder()
                    .reviewId(review.getId())
                    .image(review.getImage())
                    .build();

            reviewDtos.add(reviewDto);
        }

        return reviewDtos;
    }

}
