package com.test.drone.drone;

import com.test.drone.base.BaseFilter;
import com.test.drone.base.BaseMapper;
import com.test.drone.base.FILTER_RESULT;
import com.test.drone.core.filter.CustomParamPageable;
import com.test.drone.core.filter.SearchCriteria;
import com.test.drone.core.filter.SearchOperation;
import com.test.drone.domain.drone.Drone_;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class DroneFilter extends BaseFilter {

    public DroneFilter(BaseMapper baseMapper) {
        super(baseMapper);
    }

    @Override
    public void populateFilters() {
        super.populateFilters();
        CustomParamPageable pageable = new CustomParamPageable();
        pageable.getFilters().add(
                SearchCriteria.builder()
                        .field(Drone_.ID)
                        .operator(SearchOperation.EQUALITY)
                        .value(Integer.MAX_VALUE)
                        .build()
        );
        filtersMap.put(FILTER_RESULT.NONE, pageable);

        pageable = new CustomParamPageable();
        pageable.getFilters().add(
                SearchCriteria.builder()
                        .field(Drone_.ID)
                        .operator(SearchOperation.EQUALITY)
                        .value(NumberUtils.INTEGER_ONE)
                        .build()
        );
        filtersMap.put(FILTER_RESULT.SINGLE, pageable);
    }
}
