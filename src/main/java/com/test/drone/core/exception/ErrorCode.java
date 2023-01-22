package com.test.drone.core.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICITY("DUPLICITY"), EMPTY_VALUE("EMPTY_VALUE"), DOES_NOT_EXIST("DOES_NOT_EXIST"), INVALID_VALUE(
            "INVALID_VALUE"), REQUIRED_VALUE("REQUIRED_VALUE"), INSTANCE_ALREADY_EXIST("INSTANCE_ALREADY_EXIST"), UNAUTHORIZED(
            "UNAUTHORIZED"), INTERNAL_ERROR("INTERNAL_ERROR"), TIME_OUT("TIME_OUT");

    String code;

    ErrorCode(String code) {
        this.code = code;
    }
}
