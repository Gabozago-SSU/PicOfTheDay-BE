package com.gabozago.hack.domain.place;

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

    @OneToMany(mappedBy = "place")
    private List<PlaceImage> images  = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Curation curation;

    @OneToMany(mappedBy = "place")
    private List<PlaceLike> placeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "place")
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
