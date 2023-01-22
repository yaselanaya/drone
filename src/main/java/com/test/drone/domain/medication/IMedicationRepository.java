package com.test.drone.domain.medication;

import com.test.drone.core.base.IBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface IMedicationRepository extends JpaRepository<Medication, Integer>, CrudRepository<Medication, Integer>, JpaSpecificationExecutor<Medication>, IBaseRepository<Medication, Integer> {
}
