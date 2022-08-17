package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.place.PlaceKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceKeywordRepo extends JpaRepository<PlaceKeyword, Long> {
}
