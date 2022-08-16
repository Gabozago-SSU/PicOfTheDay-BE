package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.place.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceImageRepo extends JpaRepository<PlaceImage, Long> {
    List<PlaceImage> findByPlace(Place place);
}
