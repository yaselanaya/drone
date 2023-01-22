package com.test.drone.web.medication.dto;

import com.test.drone.core.base.BaseResourceAssembler;
import com.test.drone.domain.medication.Medication;
import com.test.drone.domain.medication.Medication_;
import com.test.drone.infraestructure.medication.MedicationFactory;
import com.test.drone.web.medication.MedicationController;
import org.springframework.stereotype.Component;

@Component
public class MedicationResourceAssembler
        extends BaseResourceAssembler<Medication, MedicationResource, MedicationDTO, Integer, MedicationFactory, MedicationController> {

    public MedicationResourceAssembler(MedicationFactory factory) {
        super(factory, MedicationController.class, MedicationResource.class, Medication_.ID);
    }
}
