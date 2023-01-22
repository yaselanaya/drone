package com.test.drone.infraestructure.drone;

import com.test.drone.core.base.BaseEntityValidationService;
import com.test.drone.core.base.BaseServiceUtil;
import com.test.drone.core.base.IMessages;
import com.test.drone.domain.drone.Drone;
import com.test.drone.web.drone.dto.DroneDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validator;

@Service
@Validated
public class DroneValidationService extends BaseEntityValidationService<Drone, DroneDTO, Integer> {

    public DroneValidationService(Validator validator, IMessages messages, BaseServiceUtil serviceUtil) {
        super(validator, messages, serviceUtil);
    }
}
