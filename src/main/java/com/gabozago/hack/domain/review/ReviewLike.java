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

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public ReviewLike(){
        this.id = null;
    }

    //==생성메소드==//
    public void setReviewLike(Review review, User user){
        this.review = review;
        this.user = user;
        review.getReviewLikes().add(this);
        user.getReviewLikes().add(this);
        review.setLikeCnt(review.getLikeCnt() + 1);
    }
}
