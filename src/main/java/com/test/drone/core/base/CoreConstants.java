package com.test.drone.core.base;

public final class CoreConstants {

    private CoreConstants() {
        throw new IllegalStateException("Core Constants");
    }

    /* Validation Error Keys */
    static final String UPDATE_ERROR_MAP_KEY_FIELD = "field";

    static final String UPDATE_ERROR_MAP_KEY_CODE = "code";

    public static final String UPDATE_ERROR_MAP_KEY_MESSAGE = "message";

    static final String UPDATE_ERROR_MAP_KEY_VALUE = "value";

    public static final String RESULT_KEY_ERROR = "error";

    public static final String RESULT_MAP_KEY_VALIDATION = "validation";

    public static final String RESULT_MAP_KEY_TIMESTAMP = "timestamp";

    public static final String RESULT_KEY_STATUS = "status";

    /* Pageable */
    public static final Integer CUSTOM_PARAM_PAGEABLE_DEFAULT_PAGE_SIZE = 10;

    public static final String CUSTOM_PARAM_PAGEABLE_DATA = "data";

    public static final String CUSTOM_PAGEABLE_SWAGGER_FILTERS_SORTS_SEARCH_OPTIONS = "The filters, sorts and search options.";

    public static final String CUSTOM_PAGEABLE_SWAGGER_EXAMPLE = "{\"search\": \"\",\"filters\":[{\"field\":\"fieldName\", \"operator\":\"EQUALITY\", \"value\":\"fieldValue\"}],\"pageable\":{\"page\":0,\"size\":10,\"orders\":[{\"direction\":\"DESC\",\"property\":\"propertyName\"}]}}";

    /* Others */
    static final String DOT = ".";

    static final String SCAPE_DOT = "\\.";

    public static final String LANGUAGE_PROPERTY = "lang";

    public static final String[] HIBERNATE_DTO_PACKAGES = new String[] {"com.test.drone.domain"};
}
