package com.gabozago.hack.domain.place;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "place_con_keyword")
@Entity
public class PlaceConKeyword {

    @Id
    @GeneratedValue
    @Column(name = "place_con_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlaceKeyword placeKeyword;

}
