package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.place.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurationRepo extends JpaRepository<Curation, Long> {
}
