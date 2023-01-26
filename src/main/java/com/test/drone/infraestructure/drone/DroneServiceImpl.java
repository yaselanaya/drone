package com.test.drone.infraestructure.drone;

import com.test.drone.core.base.ServiceBaseImpl;
import com.test.drone.core.exception.DroneException;
import com.test.drone.domain.drone.Drone;
import com.test.drone.domain.drone.DroneBatteryScheduleDTO;
import com.test.drone.domain.drone.IDroneRepository;
import com.test.drone.domain.drone.IDroneService;
import com.test.drone.domain.drone.State;
import com.test.drone.domain.medication.IMedicationService;
import com.test.drone.domain.medication.Medication;
import com.test.drone.web.drone.dto.DroneDTO;
import com.test.drone.web.drone.dto.DroneResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl extends ServiceBaseImpl<Drone, Integer, DroneDTO, DroneResource, DroneFactory, IDroneRepository> implements
        IDroneService {

    private final IMedicationService medicationService;

    public DroneServiceImpl(IDroneRepository repository, DroneFactory factory, DroneValidationService validator,
                            IMedicationService medicationService) {
        super(repository, factory, validator);
        this.medicationService = medicationService;
    }

    @Transactional
    @Override
    public void loadMedications(Integer droneId, Set<Integer> medications) throws DroneException {
        Drone drone = repository.save(validateDroneToLoad(droneId, medications));
        medicationService.setDroneForMedications(drone, medications);
    }

    @Override
    public Set<DroneBatteryScheduleDTO> findAllDronesBattery() {
        return repository.findAllDronesBattery();
    }

    @Override
    public Collection<Medication> getMedicationsByDrone(Integer droneId) throws DroneException {
        return getDroneById(droneId).getMedications();
    }

    private Drone validateDroneToLoad(Integer droneId, Set<Integer> medications) throws DroneException {
        Drone drone = getDroneById(droneId);

        if (drone.getBatteryCapacity() < 25) {
            throw new DroneException(
                    HttpStatus.BAD_REQUEST,
                    messages.getMessage("validation.error.drone.load.medication.battery")
            );
        }

        Page<Medication> medicationsToLoad = medicationService.getMedicationsToLoad(medications);

        if (medications.size() > medicationsToLoad.getSize()) {
            throw new DroneException(
                    HttpStatus.NOT_FOUND,
                    messages.getMessage("validation.error.drone.load.medication.dont.exit")
            );
        }

        drone.getMedications().addAll(medicationsToLoad.stream().collect(Collectors.toSet()));

        if (isExceedWeightLimit(drone)) {
            throw new DroneException(
                    HttpStatus.BAD_REQUEST,
                    messages.getMessage("validation.error.drone.medication.max.weight.limit")
            );
        }

        drone.setState(State.LOADING);

        return drone;
    }

    private boolean isExceedWeightLimit(Drone drone) {
        Integer weight = drone.getMedications()
                .stream()
                .map(med -> Integer.valueOf(med.getWeight()))
                .reduce(0, Integer::sum);

        return Integer.valueOf(drone.getWeightLimit()) < weight;
    }

    private Drone getDroneById(Integer droneId) throws DroneException {
        return findById(droneId).orElseThrow(() -> new DroneException(
                HttpStatus.NOT_FOUND,
                messages.getMessage("validation.error.drone.doesnt.exit")
        ));
    }
}
