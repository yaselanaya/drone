package com.test.drone.infraestructure.medication;

import com.test.drone.core.base.BaseEntityValidationService;
import com.test.drone.core.base.BaseServiceUtil;
import com.test.drone.core.base.IMessages;
import com.test.drone.domain.medication.Medication;
import com.test.drone.web.medication.dto.MedicationDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validator;

@Service
@Validated
public class MedicationValidationService extends BaseEntityValidationService<Medication, MedicationDTO, Integer> {

    public MedicationValidationService(Validator validator, IMessages messages, BaseServiceUtil serviceUtil) {
        super(validator, messages, serviceUtil);
    }
}
