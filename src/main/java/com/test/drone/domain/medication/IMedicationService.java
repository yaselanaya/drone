package com.test.drone.domain.medication;

import com.test.drone.core.base.IServiceBase;
import com.test.drone.core.exception.DroneException;
import com.test.drone.domain.drone.Drone;
import com.test.drone.web.medication.dto.MedicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface IMedicationService extends IServiceBase<Medication, Integer, MedicationDTO> {

    Medication save(MedicationDTO dto, MultipartFile file) throws DroneException;

    Medication update(MedicationDTO dto, MultipartFile file) throws DroneException;

    Page<Medication> getMedicationsToLoad(Set<Integer> medications);

    void setDroneForMedications(Drone drone, Set<Integer> medications);
}
