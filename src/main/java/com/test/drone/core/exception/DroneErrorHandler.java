package com.test.drone.core.exception;

import com.test.drone.core.base.CoreConstants;
import com.test.drone.core.base.IMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class DroneErrorHandler {

    private final IMessages messages;

    public DroneErrorHandler(IMessages messages) {
        this.messages = messages;
    }

    @ExceptionHandler(DroneException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> processValidationError(DroneException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();
        Map<String, Object> body = getResponseBody(exception.getHttpStatus(), exception.getMessage(), exception);

        return ResponseEntity.status(httpStatus).body(body);
    }

    @ExceptionHandler(DroneValidationException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> processValidation(DroneValidationException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();
        Map<String, Object> body = getResponseBody(httpStatus, exception.getMessage(), exception);
        body.put(CoreConstants.RESULT_MAP_KEY_VALIDATION, exception.getErrors());

        return ResponseEntity.status(httpStatus).body(body);
    }

    /**
     * Create the common response body part based on <strong>httpStatus</strong> and <strong>message<strong/> and current
     * timestamp
     *
     * @param httpStatus
     * @param message
     * @param exception
     * @return {@code Map}
     */
    private Map<String, Object> getResponseBody(HttpStatus httpStatus, String message, Throwable exception) {

        String i18nMessage = messages.getMessage(message);
        Map<String, Object> response = new HashMap<>();
        response.put(CoreConstants.RESULT_MAP_KEY_TIMESTAMP, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        response.put(CoreConstants.RESULT_KEY_STATUS, httpStatus.value());
        response.put(CoreConstants.UPDATE_ERROR_MAP_KEY_MESSAGE, i18nMessage);
        response.put(CoreConstants.RESULT_KEY_ERROR, httpStatus.getReasonPhrase());

        log.error(i18nMessage, exception);

        return response;
    }
}
