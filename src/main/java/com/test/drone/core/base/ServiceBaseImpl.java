package com.test.drone.core.base;

import com.test.drone.core.exception.DroneException;
import com.test.drone.core.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class ServiceBaseImpl<Entity, Id, Dto extends IDto, Resource, Factory extends IFactory<Entity, Resource, Dto>, Repository extends IBaseRepository<Entity, Id>>
        implements IServiceBase<Entity, Id, Dto> {

    protected final Repository repository;

    protected final Factory factory;

    protected IEntityValidationService<Entity, Dto, Id> validator;

    @Autowired
    protected IMessages messages;

    @Autowired
    protected BaseServiceUtil serviceUtil;

    protected ServiceBaseImpl(Repository repository, Factory factory, IEntityValidationService<Entity, Dto, Id> validator) {
        this.repository = repository;
        this.factory = factory;
        this.validator = validator;
    }

    @Override
    public Optional<Entity> findById(Id id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Entity> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Entity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Entity> findAll(Specification<Entity> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    /**
     * Method to create an entity
     *
     * @param dto
     *            object with the data
     * @return entity
     * @throws DroneException
     *             exception if the process fail
     */
    @Transactional
    @Override
    public Entity save(Dto dto) throws DroneException {
        // Convert dto to entity
        Entity entity = factory.from(dto);
        // Actions before validate
        actionsBeforeValidateForCreate(entity, dto);
        // Validate the data
        if (Objects.nonNull(validator))
            validator.validateForCreate(entity, dto);
        // Actions before persist
        actionsBeforePersistForCreate(entity, dto);
        // Create and return the entity
        entity = repository.save(entity);
        // Actions after persist
        actionsAfterPersistForCreate(entity, dto);
        // Return entity
        return entity;
    }

    /**
     * Method to update an entity
     *
     * @param dto
     *            object with the data
     * @return entity
     * @throws DroneException
     *             exception if the process fail
     */
    @Transactional
    @Override
    public Entity update(Dto dto) throws DroneException {
        // Convert dto to entity
        Entity entity = factory.from(dto);
        // Actions before validate
        actionsBeforeValidateForUpdate(entity, dto);
        // Validate the data
        if (Objects.nonNull(validator))
            validator.validateForUpdate(entity, dto);
        // Actions before persist
        entity = actionsBeforePersistForUpdate(entity, dto);
        // Update the entity
        entity = repository.save(entity);
        // Actions after persist
        entity = actionsAfterPersistForUpdate(entity, dto);
        // Return entity
        return entity;
    }

    @Transactional
    @Override
    public void delete(Id id) throws DroneException {
        // Validate the data
        if (Objects.nonNull(validator))
            validator.validateForDelete(id);
        // delete the entity
        repository.deleteById(id);
    }

    @Override
    public Map<String, Object> createError(String field, ErrorCode code, String message, Object value) {
        return serviceUtil.createError(field, code, messages.getMessage(message), value);
    }

    /**
     * Method to implement any logic before validate action.
     *
     * @param entity
     *            entity object with all the data from data base.
     * @param dto
     *            dto object with all the data from fronted.
     */
    public void actionsBeforeValidateForCreate(Entity entity, Dto dto) {
        // Implement specific actions before validate for create operation
    }

    /**
     * Method to implement any logic before persist the entity in create operation.
     *
     * @param entity
     *            entity object with all the data from data base.
     * @param dto
     *            dto object with all the data from fronted.
     * @throws DroneException
     *             throw an exception if the logic fail.
     */
    public void actionsBeforePersistForCreate(Entity entity, Dto dto) throws DroneException {
        // Implement specific actions before persist for create operation
    }

    /**
     * Method to implement any logic after persist the entity in create operation.
     *
     * @param entity
     *            entity object with all the data from data base.
     * @param dto
     *            dto object with all the data from fronted.
     * @throws DroneException
     *             throw an exception if the logic fail.
     */
    public void actionsAfterPersistForCreate(Entity entity, Dto dto) throws DroneException {
        // Implement specific actions after persist for create operation
    }

    /**
     * Method to implement any logic before validate action.
     *
     * @param entity
     *            entity object with all the data from data base.
     * @param dto
     *            dto object with all the data from fronted.
     */
    public void actionsBeforeValidateForUpdate(Entity entity, Dto dto) {
        // Implement specific actions before validate for update operation
    }

    /**
     * Method to implement any logic before persist the entity in update operation.
     *
     * @param entity
     *            entity object with all the data from data base.
     * @param dto
     *            dto object with all the data from fronted.
     * @throws DroneException
     *             throw an exception if the logic fail.
     */
    public Entity actionsBeforePersistForUpdate(Entity entity, Dto dto) throws DroneException {
        // Implement specific actions before persist for update operation
        return entity;
    }

    /**
     * Method to implement any logic after persist the entity in update operation.
     *
     * @param entity
     *            entity object with all the data from data base.
     * @param dto
     *            dto object with all the data from fronted.
     * @throws DroneException
     *             throw an exception if the logic fail.
     */
    public Entity actionsAfterPersistForUpdate(Entity entity, Dto dto) throws DroneException {
        // Implement specific actions after persist for update operation
        return entity;
    }
}
