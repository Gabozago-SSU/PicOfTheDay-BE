package com.gabozago.hack.service.review;

import com.gabozago.hack.domain.Keyword;
import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.home.Curation;
import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewImage;
import com.gabozago.hack.domain.review.ReviewLike;
import com.gabozago.hack.dto.place.PlaceSearchDto;
import com.gabozago.hack.dto.review.*;
import com.gabozago.hack.repository.KeywordRepo;
import com.gabozago.hack.repository.place.CurationRepo;
import com.gabozago.hack.repository.place.PlaceRepo;
import com.gabozago.hack.repository.place.UserRepo;
import com.gabozago.hack.repository.review.ReviewImageRepo;
import com.gabozago.hack.repository.review.ReviewLikeRepo;
import com.gabozago.hack.repository.review.ReviewRepo;
import com.gabozago.hack.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final S3Uploader s3Uploader;
    private final ReviewRepo reviewRepo;
    private final ReviewLikeRepo reviewLikeRepo;
    private final UserRepo userRepo;
    private final PlaceRepo placeRepo;
    private final ReviewImageRepo reviewImageRepo;
    private final CurationRepo curationRepo;

    private final KeywordRepo keywordRepo;

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
        List<ReviewImage> images = reviewImageRepo.findByReview(review);

        ReviewDetailDto reviewDetailDto;
        if(reviewLike.getId() != null) {
            reviewDetailDto = ReviewDetailDto.builder()
                    .userId(review.getUser().getId())
                    .profile(review.getUser().getProfileImage())
                    .placeId(review.getPlace().getId())
                    .content(review.getContent())
                    .address(review.getPlace().getAddress())
                    .userName(review.getUser().getName())
                    .reviewLikeCnt(review.getReviewLikes().size())
                    .isLike(true)
                    .rate(review.getRate())
                    .build();
        } else {
            reviewDetailDto = ReviewDetailDto.builder()
                    .userId(review.getUser().getId())
                    .profile(review.getUser().getProfileImage())
                    .placeId(review.getPlace().getId())
                    .content(review.getContent())
                    .address(review.getPlace().getAddress())
                    .userName(review.getUser().getName())
                    .reviewLikeCnt(review.getReviewLikes().size())
                    .isLike(false)
                    .rate(review.getRate())
                    .build();
        }

        for(ReviewImage image : images){
            reviewDetailDto.getImage().add(image.getImage());
        }

        for(Keyword keyword : review.getKeywords()){
            ReviewKeywordDto reviewKeywordDto = ReviewKeywordDto.builder()
                    .name(keyword.getName())
                    .build();

            reviewDetailDto.getKeywords().add(reviewKeywordDto);
        }

        return reviewDetailDto;
    }

    /**
     * 리뷰 저장
     */
    public ResponseEntity postReview(ReviewPostDto reviewPostDto, MultipartFile[] multipartFile) throws IOException {
        Place place = placeRepo.findById(reviewPostDto.getPlaceId())
                .orElseThrow(() -> new IllegalStateException("그런 장소 없음"));

        User user = userRepo.findById(reviewPostDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        Curation curation = curationRepo.findById(4l)
                .orElseThrow(() -> new IllegalStateException("없는 큐레이션"));


        Review review = new Review();
        review.setUser(user);
        review.setContent(reviewPostDto.getContent());
        review.setRate(reviewPostDto.getRate());
        review.setPlace(place);
        review.setCuration(curation);

        //[0] -> S3에 저장된 파일이름 [1] -> 이미지 경로
        for(MultipartFile multipartFile1 : multipartFile){
            String[] values = s3Uploader.upload(multipartFile1, "review");
            ReviewImage reviewImage = new ReviewImage();
            reviewImage.setImages(values);

            reviewImageRepo.save(reviewImage);

            review.setReviewImage(reviewImage);
        }

        place.avgAddRate(reviewPostDto.getRate());
        reviewRepo.save(review); // 일단 review id 생성

        //키워드 설정
        List<Keyword> keywordList = new ArrayList<Keyword>();
        for(String keywordString : reviewPostDto.getKeywords()){
            Keyword keyword;
            if (keywordRepo.findByName(keywordString).equals(Optional.empty())){
                keyword = new Keyword();
                keyword.setName(keywordString);
                keywordRepo.save(keyword);
            }else{
                keyword = keywordRepo.findByName(keywordString)
                        .orElseThrow(() -> new IllegalStateException("그런 키워드 없음"));
            }

            keywordList.add(keyword);
        }
        // 키워드 설정 끝

        Review newReview = reviewRepo.findById(review.getId())
                .map(entity -> entity.updateKeywordAndAddReviewInKeyword(keywordList))
                .orElse(review);
        reviewRepo.save(newReview);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 리뷰 삭제
     */
    public ResponseEntity deleteReview(ReviewDeleteDto reviewDeleteDto){
        Review review = reviewRepo.findById(reviewDeleteDto.getReviewId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        review.getPlace().avgDeleteRate(review.getRate());
        reviewRepo.deleteById(reviewDeleteDto.getReviewId());
        return new ResponseEntity("삭제", HttpStatus.OK);
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
