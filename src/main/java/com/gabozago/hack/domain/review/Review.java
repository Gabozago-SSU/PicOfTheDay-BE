package com.gabozago.hack.domain.review;

import com.gabozago.hack.domain.BaseEntity;
import com.gabozago.hack.domain.User;
import com.gabozago.hack.domain.place.Place;
import lombok.Data;

import javax.persistence.*;
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

    private Long likeCnt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Place place;

    @OneToMany(mappedBy = "review")
    private List<ReviewLike> review_likes = new ArrayList<>();

    //==생성메소드==//
    public void postReview(Place place){
        this.place = place;
        place.getPlace_reviews().add(this);
    }
}
