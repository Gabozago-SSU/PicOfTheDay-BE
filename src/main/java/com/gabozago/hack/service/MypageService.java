package com.gabozago.hack.service;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewLike;
import com.gabozago.hack.dto.review.ReviewLikeDto;
import com.gabozago.hack.dto.user.UserInfoDto;
import com.gabozago.hack.dto.user.UserReviewDto;
import com.gabozago.hack.repository.place.UserRepo;
import com.gabozago.hack.repository.review.ReviewLikeRepo;
import com.gabozago.hack.repository.review.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {
    private final UserRepo userRepo;
    private final ReviewRepo reviewRepo;
    private final ReviewLikeRepo reviewLikeRepo;
    public UserInfoDto getOthersUserPage(String userId){
        User user = userRepo.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(user.getId())
                .profileImage(user.getProfileImage())
                .name(user.getName())
                .mileage(user.getMileage())
                .build();

        List<Review> reviews = user.getReviews();
        for(Review review : reviews){
            UserReviewDto userReviewDto = UserReviewDto.builder()
                    .userId(user.getId())
                    .reviewId(review.getId())
                    .reviewImage(review.getImages().get(0).getImage())
                    .build();

            userInfoDto.getUserReviews().add(userReviewDto);
        }


        for(ReviewLike reviewLike : user.getReviewLikes()){
            ReviewLikeDto reviewLikeDto = ReviewLikeDto.builder()
                    .userId(user.getId())
                    .reviewId(reviewLike.getReview().getId())
                    .build();

            userInfoDto.getUserReviewLikes().add(reviewLikeDto);
        }

        return userInfoDto;
    }
}
