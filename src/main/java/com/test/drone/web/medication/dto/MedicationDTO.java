package com.test.drone.web.medication.dto;

import com.test.drone.core.base.IDto;
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
public class MedicationDTO implements IDto {

    @ApiModelProperty
    private Integer id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private Short weight;

    @ApiModelProperty
    private String code;

    @ApiModelProperty
    private String image;

    @ApiModelProperty
    private Integer droneId;
}
