package com.test.drone.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.drone.core.filter.CustomParamPageable;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseFilter implements IFilter {

    @Getter
    protected Map<FILTER_RESULT, CustomParamPageable> filtersMap;

    @Getter
    private final BaseMapper baseMapper;

    public BaseFilter(BaseMapper baseMapper) {
        this.baseMapper = baseMapper;
        filtersMap = new HashMap<>();
        populateFilters();
    }

    public String filter(FILTER_RESULT filterResult) throws JsonProcessingException {
        if (!filtersMap.containsKey(filterResult))
            return Strings.EMPTY;

        return getObjectMapper().writeValueAsString(filtersMap.get(filterResult));
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return baseMapper.objectMapper;
    }

    @Override
    public void populateFilters() {
        /* ALL */
        filtersMap.put(FILTER_RESULT.ALL, new CustomParamPageable());

        /* ANY */
        CustomParamPageable pageable = new CustomParamPageable();
        pageable.setPageSize(NumberUtils.INTEGER_ONE);
        filtersMap.put(FILTER_RESULT.ANY, pageable);
    }
}
