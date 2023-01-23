package com.test.drone.infraestructure.drone;

public final class DroneConstants {

    public DroneConstants() {
        throw new IllegalStateException("Drone Constants");
    }

    /* Mapping */
    public static final String BASE_PATH = "/drone";

    public static final String MAPPING_GET_BY_ID = "/{id}";

    public static final String MAPPING_LOAD_DRONE = "/load";
}
