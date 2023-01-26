package com.test.drone.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
public class DroneValidationException extends DroneException {

    private final transient List<Map<String, Object>> errors;

    public DroneValidationException(HttpStatus httpStatus, List<Map<String, Object>> errors) {
        super(httpStatus);
        this.errors = errors;
    }

    public DroneValidationException(HttpStatus httpStatus, String message, List<Map<String, Object>> errors) {
        super(httpStatus, message);
        this.errors = errors;
    }
}
