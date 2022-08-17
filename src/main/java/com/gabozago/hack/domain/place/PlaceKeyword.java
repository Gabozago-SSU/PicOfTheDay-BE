package com.gabozago.hack.domain.place;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "place_keyword")
@Entity
public class PlaceKeyword {

    @Id
    @GeneratedValue
    @Column(name = "place_keyword")
    private Long id;

    private String keyword;

    @OneToMany(mappedBy = "placeKeyword", cascade = CascadeType.ALL)
    private List<PlaceConKeyword> keywordList = new ArrayList<>();
}
