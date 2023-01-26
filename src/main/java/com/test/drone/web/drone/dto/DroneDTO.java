package com.test.drone.web.drone.dto;

import com.test.drone.core.base.IDto;
import com.test.drone.domain.drone.Model;
import com.test.drone.domain.drone.State;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DroneDTO implements IDto {

    @ApiModelProperty
    private Integer id;

    @ApiModelProperty
    private String serialNumber;

    @ApiModelProperty
    private Model model;

    @ApiModelProperty
    private Short weightLimit;

    @ApiModelProperty
    private Short batteryCapacity;

    @ApiModelProperty
    private State state;
}
