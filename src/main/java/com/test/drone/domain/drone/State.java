package com.test.drone.domain.drone;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.stream.Stream;

@ApiModel
@Getter
public enum State {
    IDLE("I"), LOADING("L"), LOADED("LD"), DELIVERING("D"), DELIVERED("DD"), RETURNING("R");

    private String code;

    State(String code) {
        this.code = code;
    }

    /**
     * @param name The name of the state
     * @return {@code State}
     */
    public static State getStateByName(String name)
    {
        return Stream.of(State.values())
                .filter(state -> state.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
