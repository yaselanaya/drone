package com.test.drone.core.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
public abstract class BaseResourceAssembler<Entity, Resource extends RepresentationModel<Resource>, Dto extends IDto, Id, Factory extends IFactory<Entity, Resource, Dto>, Controller extends IController<Resource, Dto, Id>>
        extends RepresentationModelAssemblerSupport<Entity, Resource> {

    private final String fieldId;

    private final Class<Controller> controllerClass;

    protected final Factory factory;

    protected Method findByIdMethod;

    public BaseResourceAssembler(Factory factory, Class<Controller> controllerClass, Class<Resource> resourceType, String fieldId) {
        super(controllerClass, resourceType);
        this.factory = factory;
        this.fieldId = fieldId;
        this.controllerClass = controllerClass;
    }

    @Override
    public Resource toModel(Entity entity) {
        Resource resource = factory.from(entity);

        if (Objects.isNull(resource) || Objects.isNull(findByIdMethod))
            return resource;

        Link resourceLink = createLink(entity);

        if (Objects.nonNull(resourceLink))
            resource.add(resourceLink);

        return resource;
    }

    public Collection<Resource> toModels(Collection<Entity> entities) {
        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }

    /**
     * Attempt to create a link for the resource given the current entity
     *
     * @param entity The current entity
     * @return The resource link or {@code null} if errors was thrown during the process
     */
    protected Link createLink(Entity entity) {

        try {
            return linkTo(controllerClass, findByIdMethod, getIdValue(entity)).withSelfRel();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Error generating resource link", e);
        }

        return null;
    }

    @SuppressWarnings("unchecked assignment")
    private Id getIdValue(Entity entity) throws IllegalAccessException, NoSuchFieldException {
        Field field = entity.getClass().getDeclaredField(fieldId);
        field.setAccessible(true);
        return (Id) field.get(entity);
    }
}
