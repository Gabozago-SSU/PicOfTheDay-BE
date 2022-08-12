package com.gabozago.hack.repository.review;

import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    Optional<List<Review>> findByPlaceOrderByIdDesc(Place place);
    Optional<List<Review>> findByPlaceOrderByLikeCntDesc(Place place);
}
