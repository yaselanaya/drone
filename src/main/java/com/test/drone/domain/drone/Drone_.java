package com.test.drone.domain.drone;

import com.test.drone.domain.medication.Medication;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Drone.class)
public class Drone_ {

    public static volatile SingularAttribute<Drone, Integer> id;

    public static volatile SingularAttribute<Drone, String> serialNumber;

    public static volatile SingularAttribute<Drone, Model> model;

    public static volatile SingularAttribute<Drone, Short> weightLimit;

    public static volatile SingularAttribute<Drone, Short> batteryCapacity;

    public static volatile SingularAttribute<Drone, State> state;

    public static volatile CollectionAttribute<Drone, Medication> medications;

    public static final String ID = "id";

    public static final String SERIAL_NUMBER = "serialNumber";

    public static final String MODEL = "model";

    public static final String WEIGHT_LIMIT = "weightLimit";

    public static final String BATTERY_CAPACITY = "batteryCapacity";

    public static final String STATE = "state";

    public static final String MEDICATIONS = "medications";
}
