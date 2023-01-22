package com.test.drone.core.base;

import com.test.drone.core.exception.DroneException;
import com.test.drone.core.exception.DroneValidationException;
import com.test.drone.core.filter.CustomParamPageable;
import com.test.drone.core.filter.SearchCriteria;
import com.test.drone.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.HateoasAwareSpringDataWebConfiguration;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
public abstract class BaseController<Entity, Dto extends IDto, Id, Resource extends RepresentationModel<Resource>, Factory extends IFactory<Entity, Resource, Dto>, SpecificController extends IController<Resource, Dto, Id>, Service extends IServiceBase<Entity, Id, Dto>, Assembler extends BaseResourceAssembler<Entity, Resource, Dto, Id, Factory, SpecificController>>
        implements IController<Resource, Dto, Id> {

    protected final Service service;

    protected final Assembler assembler;

    protected final IMessages messages;

    private PagedResourcesAssembler pagedResourcesAssembler;

    protected BaseController(Service service, Assembler assembler, IMessages messages) {
        this.service = service;
        this.assembler = assembler;
        this.messages = messages;

        HateoasAwareSpringDataWebConfiguration bean = BeanUtil.getBean(HateoasAwareSpringDataWebConfiguration.class);
        this.pagedResourcesAssembler = bean.pagedResourcesAssembler();
    }


    protected abstract String getIdField();

    protected abstract IEntitySpecification<Entity> getSpecification(List<SearchCriteria> filters, String search);

    @Override
    public ResponseEntity<Resource> findById(Id id) {
        return service.findById(id).map(assembler::toModel).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    @SuppressWarnings("unchecked call")
    public ResponseEntity<PagedModel<Resource>> findAllPaging(HttpServletRequest request) throws DroneException {
        CustomParamPageable data = configuredCustomParamPageable(request);

        Page<Entity> entities = service.findAll(getSpecification(data.getFilters(), data.getSearch()).toSpecification(),
                data.getPageable());

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(entities, assembler));
    }

    @Override
    public ResponseEntity<Resource> save(Dto dto) throws DroneException {
        try {
            return ResponseEntity.ok(assembler.toModel(service.save(dto)));
        } catch (DroneValidationException ex) {
            throw ex;
        } catch (DroneException ex) {
            log.error("Error during save operation", ex);
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @Override
    public ResponseEntity<Resource> update(Dto dto) throws DroneException {
        try {
            return ResponseEntity.ok(assembler.toModel(service.update(dto)));
        } catch (DroneValidationException ex) {
            throw ex;
        } catch (DroneException ex) {
            log.error("Error during update operation", ex);
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Id id) throws DroneException {
        service.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     *
     * @param request
     * @return {@code CustomParamPageable}
     * @throws DroneException
     */
    private CustomParamPageable configuredCustomParamPageable(HttpServletRequest request) throws DroneException {
        CustomParamPageable data = CustomParamPageable.mapRequestData(request);
        data.setIdProperty(getIdField());
        return data;
    }
}
