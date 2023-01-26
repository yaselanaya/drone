package com.test.drone.domain.drone;

import com.test.drone.core.base.IBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IDroneRepository extends JpaRepository<Drone, Integer>, CrudRepository<Drone, Integer>, JpaSpecificationExecutor<Drone>, IBaseRepository<Drone, Integer> {

    @Query("select new com.test.drone.domain.drone.DroneBatteryScheduleDTO(d.id, d.batteryCapacity) from Drone d")
    Set<DroneBatteryScheduleDTO> findAllDronesBattery();
}
