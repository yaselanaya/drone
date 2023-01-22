package com.test.drone.core.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<Entity, Id> {

    Page<Entity> findAll(Pageable pageable);

    List<Entity> findAll();

    Optional<Entity> findById(Id id);

    Page<Entity> findAll(@Nullable Specification<Entity> spec, Pageable pageable);

    <S extends Entity> S save(S entity);

    void deleteById(Id id);
}
