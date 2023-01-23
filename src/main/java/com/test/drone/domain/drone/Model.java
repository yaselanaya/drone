package com.test.drone.domain.drone;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.stream.Stream;

@ApiModel
@Getter
public enum Model {
    LIGHT_WEIGHT("LW"), MIDDLE_WEIGHT("MW"), CRUISER_WEIGHT("CW"), HEAVY_WEIGHT("HW");

    private String code;

    Model(String code) {
        this.code = code;
    }

    /**
     * @param name The name of the model
     * @return {@code Model}
     */
    public static Model getModelByName(String name)
    {
        return Stream.of(Model.values())
                .filter(model -> model.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
