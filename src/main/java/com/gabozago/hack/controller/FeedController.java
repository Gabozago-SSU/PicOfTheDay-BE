package com.gabozago.hack.controller;

import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.dto.feed.FeedReviewDto;
import com.gabozago.hack.service.feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/feed")
@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/popular")
    public List<FeedReviewDto> getFeedPopularReview() {
        return feedService.getPopularReview();
    }

    @GetMapping("/recent")
    public List<FeedReviewDto> getFeedRecentReview(){
        return feedService.getRecentReview();
    }

    @GetMapping("/search")
    public List<FeedReviewDto> getFeedSearchReview(@RequestParam(name = "search") String search){
        return feedService.getSearchFeedReview(search);
    }
}
