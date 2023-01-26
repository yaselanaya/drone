package com.test.drone.web.drone.dto;

import com.test.drone.core.base.BaseResourceAssembler;
import com.test.drone.domain.drone.Drone;
import com.test.drone.domain.drone.Drone_;
import com.test.drone.infraestructure.drone.DroneFactory;
import com.test.drone.web.drone.DroneController;
import org.springframework.stereotype.Component;

@Component
public class DroneResourceAssembler extends BaseResourceAssembler<Drone, DroneResource, DroneDTO, Integer, DroneFactory, DroneController> {

    public DroneResourceAssembler(DroneFactory factory) {
        super(factory, DroneController.class, DroneResource.class, Drone_.ID);
    }
}
