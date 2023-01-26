package com.test.drone.domain.medication;

import com.test.drone.core.base.BaseEntitySpecification;
import com.test.drone.core.filter.SearchCriteria;
import com.test.drone.core.filter.SearchOperation;

import java.util.List;

public class MedicationSpecification extends BaseEntitySpecification<Medication> {

    public MedicationSpecification(List<SearchCriteria> filters) {
        super(filters);
    }

    public MedicationSpecification(List<SearchCriteria> filters, String search) {
        super(filters, search);
    }

    @Override
    protected void populateSearchCriteria() {

        if (search.isEmpty())
            return;

        searchFilters.add(
                SearchCriteria.builder()
                        .field(Medication_.NAME)
                        .value(search)
                        .operator(SearchOperation.CONTAINS)
                        .build()
        );

        searchFilters.add(
                SearchCriteria.builder()
                        .field(Medication_.CODE)
                        .value(search)
                        .operator(SearchOperation.CONTAINS)
                        .build()
        );
    }
}
