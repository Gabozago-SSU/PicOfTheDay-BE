package com.gabozago.hack.repository.review;

import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepo extends JpaRepository<ReviewImage, Long> {
    List<ReviewImage> findByReview(Review review);
}
