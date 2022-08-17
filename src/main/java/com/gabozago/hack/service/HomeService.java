package com.gabozago.hack.service;

import com.gabozago.hack.domain.home.Banner;
import com.gabozago.hack.domain.home.Curation;
import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.dto.CurationDto;
import com.gabozago.hack.dto.CurationPlaceDto;
import com.gabozago.hack.repository.BannerRepository;
import com.gabozago.hack.repository.place.CurationRepo;
import com.gabozago.hack.repository.place.PlaceRepo;
import com.gabozago.hack.repository.review.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {
    private final BannerRepository bannerRepository;
    private final CurationRepo curationRepository;
    private final PlaceRepo placeRepo;
    private final ReviewRepo reviewRepo;

    public List<Banner> getBanner(){
        return bannerRepository.findAll();
    }

    public List<CurationDto> getCuration(){
        List<Curation> curations = curationRepository.findAll();
        List<Place> places = placeRepo.findAll();
        List<Review> reviews = reviewRepo.findAll();
        List<CurationDto> curationDtos = new ArrayList<>();


        for(Curation curation : curations) {
            CurationDto curationDto = CurationDto.builder()
                    .id(curation.getId())
                    .subtitle(curation.getSubtitle())
                    .build();

            for(Place place : places) {
                CurationPlaceDto placeDto = CurationPlaceDto.builder()
                        .curationId(place.getCuration().getId())
                        .placeId(place.getId())
                        .rate(place.getRate())
                        .category(place.getCategory())
                        .image(place.getImages().get(0).getImage())
                        .title(place.getName())
                        .reviewId(null)
                        .build();

                if(curationDto.getId() == place.getCuration().getId()){
                    curationDto.getPlaces().add(placeDto);
                }
            }

            for(Review review : reviews){
                CurationPlaceDto placeDto = CurationPlaceDto.builder()
                        .curationId(review.getCuration().getId())
                        .placeId(null)
                        .rate(review.getRate())
                        .category(null)
                        .image(review.getImages().get(0).getImage())
                        .title(null)
                        .reviewId(review.getId())
                        .build();

                if(curationDto.getId() == review.getCuration().getId()){
                    curationDto.getPlaces().add(placeDto);
                }
            }
            curationDtos.add(curationDto);
        }


        return curationDtos;


    }
}
