package com.gabozago.hack.repository.review;

import com.gabozago.hack.domain.review.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepo extends JpaRepository<ReviewLike, Long> {
}
