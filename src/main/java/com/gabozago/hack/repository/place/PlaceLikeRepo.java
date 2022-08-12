package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.place.PlaceLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceLikeRepo extends JpaRepository<PlaceLike, Long> {
    Optional<PlaceLike> findByPlaceAndUser(Place place, User user);
}
