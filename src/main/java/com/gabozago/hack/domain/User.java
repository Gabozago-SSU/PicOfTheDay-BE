package com.gabozago.hack.domain;

import com.gabozago.hack.domain.place.PlaceLike;
import com.gabozago.hack.domain.review.Review;
import com.gabozago.hack.domain.review.ReviewLike;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "user")
@Entity
@NoArgsConstructor
@DynamicInsert
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String profileImage;

    @ColumnDefault("0")
    private Integer mileage;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PlaceLike> placeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public User(Long id, String name, String profileImage, String email){
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setProfile_image(String profile_image){
        this.profileImage = profileImage;
    }

}
