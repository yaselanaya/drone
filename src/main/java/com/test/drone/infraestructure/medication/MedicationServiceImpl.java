package com.test.drone.infraestructure.medication;

import com.test.drone.core.base.IEntityValidationService;
import com.test.drone.core.base.ServiceBaseImpl;
import com.test.drone.domain.medication.IMedicationRepository;
import com.test.drone.domain.medication.IMedicationService;
import com.test.drone.domain.medication.Medication;
import com.test.drone.web.medication.dto.MedicationDTO;
import com.test.drone.web.medication.dto.MedicationResource;
import org.springframework.stereotype.Service;

@Service
public class MedicationServiceImpl extends ServiceBaseImpl<Medication, Integer, MedicationDTO, MedicationResource, MedicationFactory, IMedicationRepository> implements
        IMedicationService {

    protected MedicationServiceImpl(IMedicationRepository repository, MedicationFactory factory, IEntityValidationService<Medication, MedicationDTO, Integer> validator) {
        super(repository, factory, validator);
    }
}
