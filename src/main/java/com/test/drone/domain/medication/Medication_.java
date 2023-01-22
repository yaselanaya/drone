package com.test.drone.domain.medication;

import com.test.drone.domain.drone.Drone;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Medication.class)
public class Medication_ {

    public static volatile SingularAttribute<Drone, Integer> id;

    public static volatile SingularAttribute<Drone, String> name;

    public static volatile SingularAttribute<Drone, Short> weight;

    public static volatile SingularAttribute<Drone, String> code;

    public static volatile SingularAttribute<Drone, String> image;

    public static volatile SingularAttribute<Medication, Drone> drone;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String WEIGHT = "weight";

    public static final String CODE = "code";

    public static final String IMAGE = "image";

    public static final String DRONE = "drone";
}
