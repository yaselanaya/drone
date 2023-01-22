package com.test.drone.web.drone.dto;

import com.test.drone.core.base.IDto;
import com.test.drone.domain.drone.Model;
import com.test.drone.domain.drone.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DroneDTO implements IDto {

    private Integer id;

    private String serialNumber;

    private Model model;

    private Short weightLimit;

    private Short batteryCapacity;

    private State state;
}
