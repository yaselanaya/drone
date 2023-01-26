package com.test.drone.infraestructure.medication;

import com.test.drone.core.base.BaseEntityValidationService;
import com.test.drone.core.base.BaseServiceUtil;
import com.test.drone.core.base.IMessages;
import com.test.drone.core.exception.ErrorCode;
import com.test.drone.domain.medication.IMedicationRepository;
import com.test.drone.domain.medication.Medication;
import com.test.drone.domain.medication.Medication_;
import com.test.drone.web.medication.dto.MedicationDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Validated
public class MedicationValidationService extends BaseEntityValidationService<Medication, MedicationDTO, Integer> {

    private final IMedicationRepository repository;

    public MedicationValidationService(Validator validator, IMessages messages, BaseServiceUtil serviceUtil,
                                       IMedicationRepository repository) {
        super(validator, messages, serviceUtil);
        this.repository = repository;
    }

    protected List<Map<String, Object>> validateDeleteBusinessConstraints(Integer id) {
        List<Map<String, Object>> errors = new ArrayList<>();

        // Check if the the medication exists
        if (repository.findById(id).isEmpty()) {
            errors.add
                    (createError(
                            Medication_.ID,
                            ErrorCode.DOES_NOT_EXIST,
                            "validation.error.medication.not.exist",
                            id
                            )
                    );

            return errors;
        }

        return errors;
    }

}
