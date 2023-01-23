package com.test.drone.domain.drone;

import com.test.drone.core.base.IServiceBase;
import com.test.drone.core.exception.DroneException;
import com.test.drone.web.drone.dto.DroneDTO;

import java.util.Set;

public interface IDroneService extends IServiceBase<Drone, Integer, DroneDTO> {

    void loadMedications(Integer droneId, Set<Integer> medications) throws DroneException;
}
