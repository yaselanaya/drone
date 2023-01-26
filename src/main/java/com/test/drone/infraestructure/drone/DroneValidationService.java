package com.test.drone.infraestructure.drone;

import com.test.drone.core.base.BaseEntityValidationService;
import com.test.drone.core.base.BaseServiceUtil;
import com.test.drone.core.base.IMessages;
import com.test.drone.core.exception.ErrorCode;
import com.test.drone.domain.drone.Drone;
import com.test.drone.domain.drone.Drone_;
import com.test.drone.domain.drone.IDroneRepository;
import com.test.drone.web.drone.dto.DroneDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Validated
public class DroneValidationService extends BaseEntityValidationService<Drone, DroneDTO, Integer> {

    private final IDroneRepository repository;

    public DroneValidationService(Validator validator, IMessages messages, BaseServiceUtil serviceUtil, IDroneRepository repository) {
        super(validator, messages, serviceUtil);
        this.repository = repository;
    }

    protected List<Map<String, Object>> validateDeleteBusinessConstraints(Integer id) {
        List<Map<String, Object>> errors = new ArrayList<>();

        // Check if the the drone exists
        if (repository.findById(id).isEmpty()) {
            errors.add
                    (createError(
                            Drone_.ID,
                            ErrorCode.DOES_NOT_EXIST,
                            "validation.error.drone.not.exist",
                            id
                            )
                    );

            return errors;
        }

        return errors;
    }
}
