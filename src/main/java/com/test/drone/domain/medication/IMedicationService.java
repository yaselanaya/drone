package com.test.drone.domain.medication;

import com.test.drone.core.base.IServiceBase;
import com.test.drone.domain.drone.Drone;
import com.test.drone.web.medication.dto.MedicationDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface IMedicationService extends IServiceBase<Medication, Integer, MedicationDTO> {

    Page<Medication> getMedicationsToLoad(Set<Integer> medications);

    void setDroneForMedications(Drone drone, Set<Integer> medications);
}
