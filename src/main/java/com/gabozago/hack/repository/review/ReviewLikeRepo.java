package com.gabozago.hack.repository.review;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewLikeRepo extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByUserAndReview(User user, Review review);

}
