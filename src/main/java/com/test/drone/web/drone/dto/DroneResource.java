package com.test.drone.web.drone.dto;

import com.test.drone.domain.drone.Model;
import com.test.drone.domain.drone.State;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DroneResource extends RepresentationModel<DroneResource> {

    private Integer id;

    private String serialNumber;

    private Model model;

    private Short weightLimit;

    private Short batteryCapacity;

    private State state;
}
