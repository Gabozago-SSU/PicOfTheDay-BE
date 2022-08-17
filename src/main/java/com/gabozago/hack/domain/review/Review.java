package com.gabozago.hack.domain.review;

import com.gabozago.hack.domain.BaseEntity;
import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.home.Curation;
import com.gabozago.hack.domain.place.Place;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "review")
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String content;

    @Column(precision = 2, scale = 1)
    private BigDecimal rate;

    @ColumnDefault("0")
    private Integer likeCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Curation curation;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImage> images = new ArrayList<>();

    //==생성메소드==//
    public void postReview(Place place){
        this.place = place;
        place.getPlaceReviews().add(this);
    }

    public void setReviewImage(ReviewImage reviewImage){
        this.images.add(reviewImage);
        reviewImage.setReview(this);
    }


}
