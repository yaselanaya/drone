package com.test.drone.core.base;

import com.test.drone.core.exception.DroneException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;

@Slf4j
public abstract class BaseFactory<Entity, Resource, Dto extends IDto> implements IFactory<Entity, Resource, Dto> {

    protected final ModelMapper mapper;

    protected IMessages messages;

    protected final Class<Entity> entityClass;

    protected final Class<Resource> resourceClass;

    public BaseFactory(Class<Entity> entityClass, Class<Resource> resourceClass, IMessages messages) {
        this.messages = messages;
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.entityClass = entityClass;
        this.resourceClass = resourceClass;
        configureDtoMapper();
        configureEntityMapper();
    }

    public Entity from(Dto dto) throws DroneException {

        Entity entity;

        try {
            entity = entityClass.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            String msg = messages.getMessage("default.error.map.dto.entity.resource",
                    new Object[] { entityClass.toString(), resourceClass.toString() });
            throw new DroneException(HttpStatus.UNPROCESSABLE_ENTITY, msg);
        }

        this.mapper.map(dto, entity);

        // Do anything else
        dtoToEntityActions(entity, dto);

        return entity;
    }

    public Resource from(Entity entity) {

        Resource resource;

        try {
            resource = resourceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        this.mapper.map(entity, resource);

        // Do anything else
        entityToResourceActions(resource, entity);

        return resource;
    }

    /**
     * Implement specific logic relative to the mapping
     *
     * @param entity
     * @param dto
     */
    protected void dtoToEntityActions(Entity entity, Dto dto) {
        // Implement specific logic for the process.
    }

    /**
     * Implement specific logic relative to the mapping
     *
     * @param resource
     * @param entity
     */
    protected void entityToResourceActions(Resource resource, Entity entity) {
        // Implement specific logic for the process.
    }

    /**
     * Configure model mapper
     */
    protected void configureDtoMapper() {
        // Configure mapper for the process of dto mapping.
    }

    /**
     * Configure model mapper
     */
    protected void configureEntityMapper() {
        // Configure mapper for the process of entity mapping.
    }
}
