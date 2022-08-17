package com.gabozago.hack.domain.home;

import com.gabozago.hack.domain.place.Place;
import com.gabozago.hack.domain.review.Review;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "curation")
@Entity
public class Curation {

    @Id
    @GeneratedValue
    @Column(name = "curation_id")
    private Long id;

    private String subtitle;

    @OneToMany(mappedBy = "curation",cascade = CascadeType.ALL)
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "curation", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}
