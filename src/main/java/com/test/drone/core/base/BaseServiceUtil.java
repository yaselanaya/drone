package com.test.drone.core.base;

import com.test.drone.core.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BaseServiceUtil {

    /**
     *
     * @param field
     * @param code
     * @param message
     * @param value
     * @return {@code Map}
     */
    public Map<String, Object> createError(String field, ErrorCode code, String message, Object value) {
        return createError(field, code.getCode(), message, value);
    }

    /**
     *
     * @param field
     * @param code
     * @param message
     * @param value
     * @return {@code Map}
     */
    public Map<String, Object> createError(String field, String code, String message, Object value) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(CoreConstants.UPDATE_ERROR_MAP_KEY_FIELD, field);
        errorMap.put(CoreConstants.UPDATE_ERROR_MAP_KEY_CODE, code);
        errorMap.put(CoreConstants.UPDATE_ERROR_MAP_KEY_MESSAGE, message);
        errorMap.put(CoreConstants.UPDATE_ERROR_MAP_KEY_VALUE, value);
        return errorMap;
    }
}
