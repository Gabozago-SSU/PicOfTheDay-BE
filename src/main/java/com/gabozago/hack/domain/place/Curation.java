package com.gabozago.hack.domain.place;

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

    @OneToMany(mappedBy = "curation")
    private List<Place> places = new ArrayList<>();
}
