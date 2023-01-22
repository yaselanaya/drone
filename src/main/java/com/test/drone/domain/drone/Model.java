package com.test.drone.domain.drone;

import lombok.Getter;

@Getter
public enum Model {
    LIGHT_WEIGHT("LW"), MIDDLE_WEIGHT("MW"), CRUISER_WEIGHT("CW"), HEAVY_WEIGHT("HW");

    private String code;

    private Model(String code) {
        this.code = code;
    }
}
