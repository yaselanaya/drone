package com.test.drone.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IFilter {

    void populateFilters();

    String filter(FILTER_RESULT filterResult) throws JsonProcessingException;

    ObjectMapper getObjectMapper();

}
