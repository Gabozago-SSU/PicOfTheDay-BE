package com.gabozago.hack.domain.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabozago.hack.domain.home.Curation;
import com.gabozago.hack.domain.review.Review;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "place")
@Entity
public class Place {

    @Id
    @GeneratedValue
    @Column(name = "place_id")
    private Long id;

    private String name;
    private String address;
    private Long rate;
    private String category;
    private String phoneNumber;

    private String content;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceImage> images  = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    private Curation curation;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceLike> placeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Review> placeReviews = new ArrayList<>();

    //==생성메소드==//
    public void setPlaceImage(PlaceImage placeImage){
        this.images.add(placeImage);
        placeImage.setPlace(this);
    }

    public void setCuration(Curation curation){
        this.curation = curation;
        curation.getPlaces().add(this);
    }
}
