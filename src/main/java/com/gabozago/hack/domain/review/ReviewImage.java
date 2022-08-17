package com.gabozago.hack.domain.review;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "review_image")
@Entity
public class ReviewImage {

    @Id
    @GeneratedValue
    @Column(name = "review_image_id")
    private Long id;

    private String deleteImage;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public void setImages(String[] values){
        this.deleteImage = values[0];
        this.image = values[1];
    }
}
