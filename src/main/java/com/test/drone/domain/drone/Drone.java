package com.test.drone.domain.drone;

import com.test.drone.core.validation.Common;
import com.test.drone.core.validation.OnUpdate;
import com.test.drone.domain.medication.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Builder
@Table(name = "drone")
public class Drone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "validation.error.drone.notnull.id", groups = OnUpdate.class)
    private Integer id;

    @Column(name = "serial_number")
    @NotNull(message = "validation.error.drone.notnull.serial.number", groups = Common.class)
    private String serialNumber;

    @Column(name = "model")
    @NotNull(message = "validation.error.drone.notnull.model", groups = Common.class)
    private Model model;

    @Column(name = "weight_limit")
    @NotNull(message = "validation.error.drone.notnull.weight.limit", groups = Common.class)
    @Max(value = 500, message = "validation.error.drone.max.weight.limit", groups = Common.class)
    @Min(value = 0, message = "validation.error.drone.min.weight.limit", groups = Common.class)
    private Short weightLimit;

    @Column(name = "battery_capacity")
    @NotNull(message = "validation.error.drone.notnull.battery.capacity", groups = Common.class)
    @Max(value = 100, message = "validation.error.drone.max.battery.capacity", groups = Common.class)
    @Min(value = 0, message = "validation.error.drone.min.battery.capacity", groups = Common.class)
    private Short batteryCapacity;

    @Column(name = "state")
    @NotNull(message = "validation.error.drone.notnull.state", groups = Common.class)
    private State state;

    @OneToMany(mappedBy = "drone", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderColumn(name = "id")
    private Collection<Medication> medications;
}
