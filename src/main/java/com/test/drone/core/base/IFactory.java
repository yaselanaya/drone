package com.test.drone.core.base;

import com.test.drone.core.exception.DroneException;

import javax.validation.constraints.NotNull;

public interface IFactory<Entity, Resource, Dto extends IDto> {

    Entity from(@NotNull Dto dto) throws DroneException;

    Resource from(@NotNull Entity entity);
}
