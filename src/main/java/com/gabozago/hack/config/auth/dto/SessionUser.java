package com.gabozago.hack.config.auth.dto;

import com.gabozago.hack.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.picture = user.getProfileImage();
    }
}