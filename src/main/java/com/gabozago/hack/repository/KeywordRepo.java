package com.gabozago.hack.repository;

import com.gabozago.hack.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepo extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByName(String name);
}
