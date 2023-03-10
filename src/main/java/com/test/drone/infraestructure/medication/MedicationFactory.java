package com.test.drone.infraestructure.medication;

import com.test.drone.core.base.BaseFactory;
import com.test.drone.core.base.IMessages;
import com.test.drone.domain.medication.Medication;
import com.test.drone.web.medication.dto.MedicationDTO;
import com.test.drone.web.medication.dto.MedicationResource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedicationFactory extends BaseFactory<Medication, MedicationResource, MedicationDTO> {

    public MedicationFactory(IMessages messages) {
        super(Medication.class, MedicationResource.class, messages);
    }

    @Override
    protected void entityToResourceActions(MedicationResource resource, Medication entity) {
        Optional.ofNullable(entity.getDrone()).ifPresent(drone -> resource.setDroneId(drone.getId()));
    }
}
