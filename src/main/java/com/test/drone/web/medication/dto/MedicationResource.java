package com.test.drone.web.medication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MedicationResource extends RepresentationModel<MedicationResource> {

    private Integer id;

    private String name;

    private Short weight;

    private String code;

    private String image;

    private Integer droneId;
}
