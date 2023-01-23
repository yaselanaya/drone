package com.test.drone.infraestructure.medication;

import com.test.drone.core.base.BaseEntityValidationService;
import com.test.drone.core.base.BaseServiceUtil;
import com.test.drone.core.base.IMessages;
import com.test.drone.core.exception.ErrorCode;
import com.test.drone.domain.drone.Drone;
import com.test.drone.domain.medication.Medication;
import com.test.drone.domain.medication.Medication_;
import com.test.drone.web.medication.dto.MedicationDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Validated
public class MedicationValidationService extends BaseEntityValidationService<Medication, MedicationDTO, Integer> {

    public MedicationValidationService(Validator validator, IMessages messages, BaseServiceUtil serviceUtil) {
        super(validator, messages, serviceUtil);
    }

    @Override
    protected List<Map<String, Object>> validateCommonBusinessConstraints(Medication medication) {
        List<Map<String, Object>> errors = new ArrayList<>();

        // Check if the the drone is null
        Drone drone = medication.getDrone();
        if (Objects.isNull(drone)) {
            errors.add(createError(Medication_.DRONE, ErrorCode.DOES_NOT_EXIST,
                    "validation.error.medication.not.exist.drone", Strings.EMPTY));

            return errors;
        }

        return errors;
    }
}
