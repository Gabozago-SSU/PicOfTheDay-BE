package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.place.PlaceKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceKeywordRepo extends JpaRepository<PlaceKeyword, Long> {
    Optional<List<PlaceKeyword>> findByKeywordContaining(String keyword);

    Optional<List<PlaceKeyword>> findByKeyword(String keyword);

}
