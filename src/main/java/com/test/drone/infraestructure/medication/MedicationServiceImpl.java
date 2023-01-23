package com.test.drone.infraestructure.medication;

import com.test.drone.core.base.ServiceBaseImpl;
import com.test.drone.core.filter.SearchCriteria;
import com.test.drone.core.filter.SearchOperation;
import com.test.drone.domain.drone.Drone;
import com.test.drone.domain.drone.IDroneRepository;
import com.test.drone.domain.medication.IMedicationRepository;
import com.test.drone.domain.medication.IMedicationService;
import com.test.drone.domain.medication.Medication;
import com.test.drone.domain.medication.MedicationSpecification;
import com.test.drone.domain.medication.Medication_;
import com.test.drone.web.medication.dto.MedicationDTO;
import com.test.drone.web.medication.dto.MedicationResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MedicationServiceImpl extends ServiceBaseImpl<Medication, Integer, MedicationDTO, MedicationResource, MedicationFactory, IMedicationRepository>
        implements IMedicationService {

    private final IDroneRepository droneRepository;

    protected MedicationServiceImpl(IMedicationRepository repository, MedicationFactory factory,
                                    MedicationValidationService validator, IDroneRepository droneRepository) {
        super(repository, factory, validator);
        this.droneRepository = droneRepository;
    }

    @Override
    public void actionsBeforeValidateForCreate(Medication medication, MedicationDTO dto) {
        setDroneToMedication(medication, dto.getDroneId());
    }

    @Override
    public void actionsBeforeValidateForUpdate(Medication medication, MedicationDTO dto) {
        setDroneToMedication(medication, dto.getDroneId());
    }

    private void setDroneToMedication(Medication medication, Integer droneId) {
        droneRepository.findById(droneId).ifPresent(medication::setDrone);
    }

    @Override
    public Page<Medication> getMedicationsToLoad(Set<Integer> medications) {
        List<SearchCriteria> criteria = new ArrayList<>(){{
            add(SearchCriteria.builder()
                    .field(Medication_.ID)
                    .operator(SearchOperation.IN)
                    .value(medications)
                    .build()
            );
        }};

        return findAll(new MedicationSpecification(criteria).toSpecification(), Pageable.unpaged());
    }

    @Override
    public void setDroneForMedications(Drone drone, Set<Integer> medications) {
        repository.setDroneForMedications(drone, medications);
    }
}
