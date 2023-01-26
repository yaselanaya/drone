package com.test.drone.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DroneException extends Exception {

    private final HttpStatus httpStatus;

    public DroneException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        this.httpStatus = httpStatus;
    }

    public DroneException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
