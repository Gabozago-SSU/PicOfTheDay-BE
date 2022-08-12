package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.place.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceImageRepo extends JpaRepository<PlaceImage, Long> {
}
