package com.gabozago.hack.domain.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabozago.hack.domain.place.Place;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "place_image")
@Entity
public class PlaceImage {

    @Id
    @GeneratedValue
    @Column(name = "place_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @Column(length = 1000)
    private String image;
}
