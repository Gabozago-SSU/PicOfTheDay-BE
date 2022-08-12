package com.gabozago.hack.domain.review;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.place.Place;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "review_like")
@Entity
public class ReviewLike {

    @Id
    @GeneratedValue
    @Column(name = "review_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User user;

    //==생성메소드==//
    public void setReviewLike(Review review, User user){
        this.review = review;
        this.user = user;
        review.getReview_likes().add(this);
        user.getReview_likes().add(this);
        review.setLikeCnt(review.getLikeCnt() + 1);
    }
}
