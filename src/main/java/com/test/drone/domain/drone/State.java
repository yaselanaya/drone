package com.test.drone.domain.drone;

import lombok.Getter;

@Getter
public enum State {
    IDLE("I"), LOADING("L"), LOADED("LD"), DELIVERING("D"), DELIVERED("DD"), RETURNING("R");

    private String code;

    private State(String code) {
        this.code = code;
    }
}
