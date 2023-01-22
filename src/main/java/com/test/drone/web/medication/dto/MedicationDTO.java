package com.test.drone.web.medication.dto;

import com.test.drone.core.base.IDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MedicationDTO implements IDto {

    private Integer id;

    private String name;

    private Short weight;

    private String code;

    private String image;
}
