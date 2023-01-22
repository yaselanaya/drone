package com.test.drone.infraestructure.drone;

import com.test.drone.core.base.ServiceBaseImpl;
import com.test.drone.domain.drone.Drone;
import com.test.drone.domain.drone.IDroneRepository;
import com.test.drone.domain.drone.IDroneService;
import com.test.drone.web.drone.dto.DroneDTO;
import com.test.drone.web.drone.dto.DroneResource;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl extends ServiceBaseImpl<Drone, Integer, DroneDTO, DroneResource, DroneFactory, IDroneRepository> implements
        IDroneService {

    public DroneServiceImpl(IDroneRepository repository, DroneFactory factory, DroneValidationService validator) {
        super(repository, factory, validator);
    }
}
