package com.test.drone.domain.drone;

import com.test.drone.core.base.BaseEntitySpecification;
import com.test.drone.core.filter.SearchCriteria;

import java.util.List;

public class DroneSpecification extends BaseEntitySpecification<Drone> {

        public DroneSpecification(List<SearchCriteria> filters, String search) {
            super(filters, search);
        }
}
