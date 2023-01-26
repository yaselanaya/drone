package com.test.drone.domain.medication;

import com.test.drone.core.validation.Common;
import com.test.drone.core.validation.OnUpdate;
import com.test.drone.domain.drone.Drone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Builder
@Table(name = "medication")
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "validation.error.medication.notnull.id", groups = OnUpdate.class)
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "validation.error.medication.notnull.name", groups = Common.class)
    @Pattern(regexp = "^$|[A-Za-z0-9-_]+$", message = "validation.error.medication.regex.name", groups = Common.class)
    private String name;

    @Column(name = "weight")
    @NotNull(message = "validation.error.medication.notnull.weight", groups = Common.class)
    private Short weight;

    @Column(name = "code")
    @NotNull(message = "validation.error.medication.notnull.code", groups = Common.class)
    @Pattern(regexp = "^$|[A-Z0-9_]+$", message = "validation.error.medication.regex.code", groups = Common.class)
    private String code;

    @Column(name = "image")
    private String image;

    @JoinColumn(name = "drone_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Drone drone;
}
