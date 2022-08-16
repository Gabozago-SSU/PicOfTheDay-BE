package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
    Optional<List<Place>> findByCategory(String category);

    Optional<List<Place>> findByNameContaining(String name);

    Optional<Place> findByName(String name);
}
