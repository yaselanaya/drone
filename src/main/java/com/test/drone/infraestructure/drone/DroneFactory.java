package com.test.drone.infraestructure.drone;

import com.test.drone.core.base.BaseFactory;
import com.test.drone.core.base.IMessages;
import com.test.drone.domain.drone.Drone;
import com.test.drone.web.drone.dto.DroneDTO;
import com.test.drone.web.drone.dto.DroneResource;
import org.springframework.stereotype.Component;

@Component
public class DroneFactory extends BaseFactory<Drone, DroneResource, DroneDTO> {

    public DroneFactory(IMessages messages) {
        super(Drone.class, DroneResource.class, messages);
    }
}
