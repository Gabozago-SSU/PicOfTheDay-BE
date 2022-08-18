package com.gabozago.hack.controller;

import com.gabozago.hack.dto.place.PlaceDto;
import com.gabozago.hack.dto.place.PlaceLikeDto;
import com.gabozago.hack.dto.place.PlaceReviewDto;
import com.gabozago.hack.dto.place.PlaceSimilarDto;
import com.gabozago.hack.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/place")
@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    /**
     * 장소 디테일
     */
    @GetMapping("")
    public PlaceDto getPlace(@RequestParam(name = "placeId") Long place_id,
                             @RequestParam(name = "userId") Long user_id) {
        return placeService.getPlace(place_id, user_id);
    }

    @PostMapping("/like")
    public ResponseEntity postLike(@RequestBody PlaceLikeDto placeLikeDto){
        return placeService.likePlace(placeLikeDto);
    }

    @DeleteMapping("/unlike")
    public ResponseEntity postUnLike(@RequestBody PlaceLikeDto placeLikeDto){
        return placeService.unlikePlace(placeLikeDto);
    }

    @GetMapping("/popular")
    public List<PlaceReviewDto> getPopularReview(@RequestParam(name = "placeId") Long place_id,
                                                 @RequestParam(name = "userId") Long user_id) {
        return placeService.getPopularReview(place_id,user_id);
    }

    @GetMapping("/recent")
    public List<PlaceReviewDto> getRecentReview(@RequestParam(name = "placeId") Long place_id,
                                                @RequestParam(name = "userId") Long user_id){
        return placeService.getRecentReview(place_id, user_id);
    }

    @GetMapping("/similar")
    public List<PlaceSimilarDto> getSimilarPlace(@RequestParam(name = "category") String category) {
        return placeService.getSimilarPlace(category);
    }
}
