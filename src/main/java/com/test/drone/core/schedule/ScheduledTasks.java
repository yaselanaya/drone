package com.test.drone.core.schedule;

import com.test.drone.domain.drone.DroneBatteryScheduleDTO;
import com.test.drone.domain.drone.IDroneService;
import com.test.drone.domain.event_log.EventLog;
import com.test.drone.domain.event_log.IEventLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class ScheduledTasks {

    private final IDroneService droneService;

    private final IEventLogRepository eventLogRepository;

    public ScheduledTasks(IDroneService droneService, IEventLogRepository eventLogRepository) {
        this.droneService = droneService;
        this.eventLogRepository = eventLogRepository;
    }

    @Scheduled(cron = "${cron.expression.each.one.hour}")
    public void checkDroneBatteryLevel() {
        log.info("Schedule task for checking the battery of the drones");
        final LocalDateTime currentDate = LocalDateTime.now();

        Set<DroneBatteryScheduleDTO> dronesBattery = droneService.findAllDronesBattery();

        Set<EventLog> logs = dronesBattery.stream()
                .map(drone -> buildEventLog(drone, currentDate))
                .collect(Collectors.toSet());

        eventLogRepository.saveAll(logs);
    }

    private EventLog buildEventLog(DroneBatteryScheduleDTO drone, LocalDateTime currentDate) {
        return EventLog.builder()
                .droneId(drone.getId())
                .batteryCapacity(drone.getBatteryCapacity())
                .createdDate(currentDate)
                .build();
    }
}
