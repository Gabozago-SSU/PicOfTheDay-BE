package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.place.PlaceConKeyword;
import com.gabozago.hack.domain.place.PlaceKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceConKeywordRepo extends JpaRepository<PlaceConKeyword,Long> {
    List<PlaceConKeyword> findByPlace(Place place);

    List<PlaceConKeyword> findByPlaceKeyword(PlaceKeyword placeKeyword);
}
