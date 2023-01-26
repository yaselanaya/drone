package com.test.drone.domain.drone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DroneBatteryScheduleDTO {

    private Integer id;

    private Short batteryCapacity;
}
