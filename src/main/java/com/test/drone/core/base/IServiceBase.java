package com.test.drone.core.base;

import com.test.drone.core.exception.DroneException;
import com.test.drone.core.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface IServiceBase<Entity, Id, Dto extends IDto> {

    Optional<Entity> findById(Id id);

    Collection<Entity> findAll();

    Page<Entity> findAll(Pageable pageable);

    Page<Entity> findAll(Specification<Entity> specification, Pageable pageable);

    Entity save(Dto entity) throws DroneException;

    Entity update(Dto entity) throws DroneException;

    void delete(Id id) throws DroneException;

    default Map<String, Object> createError(String field, ErrorCode code, String message, Object value) {
        return new HashMap<>();
    }
}
