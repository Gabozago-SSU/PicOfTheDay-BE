package com.gabozago.hack.controller;

import com.gabozago.hack.domain.home.Banner;
import com.gabozago.hack.domain.home.Curation;
import com.gabozago.hack.dto.CurationDto;
import com.gabozago.hack.dto.feed.FeedPlaceSearchDto;
import com.gabozago.hack.dto.place.PlaceSearchDto;
import com.gabozago.hack.service.HomeService;
import com.gabozago.hack.service.feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
    private final FeedService feedService;

    @GetMapping("/banner")
    public List<Banner> getBanner(){
        return homeService.getBanner();
    }

    @GetMapping("/curation")
    public List<CurationDto> getCuration(){
        return homeService.getCuration();
    }

    @GetMapping("/search")
    public List<PlaceSearchDto> getFeedSearchReview(@RequestParam(name = "search") String search){
        return feedService.getSearchFeedReview(search);
    }

}
