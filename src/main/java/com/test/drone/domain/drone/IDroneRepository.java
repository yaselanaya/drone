package com.test.drone.domain.drone;

import com.test.drone.core.base.IBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDroneRepository extends JpaRepository<Drone, Integer>, CrudRepository<Drone, Integer>, JpaSpecificationExecutor<Drone>, IBaseRepository<Drone, Integer> {
}
