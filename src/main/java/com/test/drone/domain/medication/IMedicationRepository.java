package com.test.drone.domain.medication;

import com.test.drone.core.base.IBaseRepository;
import com.test.drone.domain.drone.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface IMedicationRepository extends JpaRepository<Medication, Integer>, CrudRepository<Medication, Integer>, JpaSpecificationExecutor<Medication>, IBaseRepository<Medication, Integer> {

    @Modifying
    @Query(value = "UPDATE Medication m SET m.drone = :drone where m.id IN :medicationsId")
    void setDroneForMedications(@Param("drone") Drone drone, @Param("medicationsId") Set<Integer> medicationsId);
}
