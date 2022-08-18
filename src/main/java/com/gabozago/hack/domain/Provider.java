package com.gabozago.hack.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    KAKAO("kakao"),
    GOOGLE("google");

    private final String provider;

}
