package com.test.drone.web.medication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoadDroneDTO {

    private Integer id;

    private Set<Integer> medications;
}
