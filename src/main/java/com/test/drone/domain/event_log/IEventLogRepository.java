package com.test.drone.domain.event_log;

import com.test.drone.core.base.IBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface IEventLogRepository extends JpaRepository<EventLog, Long>, CrudRepository<EventLog, Long>, JpaSpecificationExecutor<EventLog>, IBaseRepository<EventLog, Long> {
}
