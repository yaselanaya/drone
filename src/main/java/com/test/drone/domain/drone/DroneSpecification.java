package com.test.drone.domain.drone;

import com.test.drone.core.base.BaseEntitySpecification;
import com.test.drone.core.filter.SearchCriteria;
import com.test.drone.core.filter.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DroneSpecification extends BaseEntitySpecification<Drone> {

    public DroneSpecification(List<SearchCriteria> filters, String search) {
        super(filters, search);
    }

    public DroneSpecification(List<SearchCriteria> filters) {
        super(filters);
    }

    @Override
    protected void populateSearchCriteria() {

        if (search.isEmpty())
            return;

        searchFilters.add(
                SearchCriteria.builder()
                        .field(Drone_.SERIAL_NUMBER)
                        .value(search)
                        .operator(SearchOperation.CONTAINS)
                        .build()
        );
    }

    @Override
    protected Specification<Drone> buildSpecification(SearchCriteria criteria) {
        String field = criteria.getField();
        Object value = criteria.getValue();

        if (Drone_.STATE.equals(field)) {
            return byState((String) value);
        } else if(Drone_.MODEL.equals(field)) {
            return byModel((String) value);
        }

        return super.buildSpecification(criteria);
    }

    private static Specification<Drone> byState(String stateFilter) {
        return (root, query, builder) -> builder
                .equal(root.get(Drone_.STATE), State.getStateByName(stateFilter));
    }

    private static Specification<Drone> byModel(String stateFilter) {
        return (root, query, builder) -> builder
                .equal(root.get(Drone_.MODEL), Model.getModelByName(stateFilter));
    }
}
