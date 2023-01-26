package com.test.drone.core.base;

import com.test.drone.core.exception.DroneException;
import com.test.drone.core.exception.ErrorCode;

import java.util.Map;

public interface IEntityValidationService<Entity, DTO extends IDto, Id> {

    /**
     *
     * @param entity
     * @throws DroneException
     */
    void validateForCreate(Entity entity) throws DroneException;

    /**
     *
     * @param entity
     * @throws DroneException
     */
    void validateForCreate(Entity entity, DTO dto) throws DroneException;

    /**
     *
     * @param entity
     * @throws DroneException
     */
    void validateForUpdate(Entity entity) throws DroneException;

    /**
     *
     * @param entity
     * @throws DroneException
     */
    void validateForUpdate(Entity entity, DTO dto) throws DroneException;

    /**
     *
     * @param id
     * @throws DroneException
     */
    void validateForDelete(Id id) throws DroneException;

    /**
     *
     * @param field
     * @param code
     * @param messageKey
     * @param value
     * @return {@code List}
     */
    Map<String, Object> createError(String field, ErrorCode code, String messageKey, Object value);
}
