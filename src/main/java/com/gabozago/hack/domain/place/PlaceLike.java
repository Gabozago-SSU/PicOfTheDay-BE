package com.gabozago.hack.domain.place;

import com.gabozago.hack.domain.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "place_like")
@Entity
public class PlaceLike {

    @Id
    @GeneratedValue
    @Column(name = "place_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User user;

    //==생성메소드==//
    public void setPlaceLike(Place place, User user){
        this.place = place;
        this.user = user;
        place.getPlace_likes().add(this);
        user.getPlace_likes().add(this);
    }
}
