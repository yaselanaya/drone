package com.test.drone.domain.medication;

import com.test.drone.core.base.BaseEntitySpecification;
import com.test.drone.core.filter.SearchCriteria;

import java.util.List;

public class MedicationSpecification extends BaseEntitySpecification<Medication> {

    public MedicationSpecification(List<SearchCriteria> filters, String search) {
        super(filters, search);
    }
}
