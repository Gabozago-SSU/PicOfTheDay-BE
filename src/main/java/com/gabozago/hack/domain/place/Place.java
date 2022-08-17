package com.gabozago.hack.domain.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabozago.hack.domain.home.Curation;
import com.gabozago.hack.domain.review.Review;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @ColumnDefault("0")
    @Column(precision = 2, scale = 1)
    private BigDecimal rate;
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

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceConKeyword> placeConKeywords = new ArrayList<>();

    //==생성메소드==//
    public void setPlaceImage(PlaceImage placeImage){
        this.images.add(placeImage);
        placeImage.setPlace(this);
    }

    public void setCuration(Curation curation){
        this.curation = curation;
        curation.getPlaces().add(this);
    }

    //==계산 메소드==//
    public void avgAddRate(BigDecimal rate){
        if(placeReviews.size() == 1) {
            this.rate = this.rate.add(rate);
        } else {
            BigDecimal div = new BigDecimal("2");
            this.rate = this.rate.add(rate).divide(div ,1, RoundingMode.HALF_EVEN);
        }
    }

    public void avgDeleteRate(BigDecimal rate) {
        BigDecimal mul = new BigDecimal("2");
        this.rate = this.rate.multiply(mul).subtract(rate);
    }
}
