package com.test.drone.web.medication;

import com.test.drone.core.base.BaseController;
import com.test.drone.core.base.CoreConstants;
import com.test.drone.core.base.IEntitySpecification;
import com.test.drone.core.base.IMessages;
import com.test.drone.core.exception.DroneException;
import com.test.drone.core.filter.SearchCriteria;
import com.test.drone.domain.medication.IMedicationService;
import com.test.drone.domain.medication.Medication;
import com.test.drone.domain.medication.MedicationSpecification;
import com.test.drone.domain.medication.Medication_;
import com.test.drone.infraestructure.medication.MedicationConstants;
import com.test.drone.infraestructure.medication.MedicationFactory;
import com.test.drone.web.medication.dto.MedicationDTO;
import com.test.drone.web.medication.dto.MedicationResource;
import com.test.drone.web.medication.dto.MedicationResourceAssembler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = MedicationConstants.BASE_PATH)
@Api(tags = { "Medication Controller" })
@SwaggerDefinition(tags = { @Tag(name = "Medication Controller", description = "Operations pertaining to medications") })
public class MedicationController  extends
        BaseController<Medication, MedicationDTO, Integer, MedicationResource, MedicationFactory, MedicationController, IMedicationService, MedicationResourceAssembler> {

    protected MedicationController(IMedicationService service, MedicationResourceAssembler assembler, PagedResourcesAssembler<Medication> pagedResourcesAssembler, IMessages messages) {
        super(service, assembler, pagedResourcesAssembler, messages);
    }

    @Override
    protected String getIdField() {
        return Medication_.ID;
    }

    @Override
    protected IEntitySpecification<Medication> getSpecification(List<SearchCriteria> filters, String search) {
        return new MedicationSpecification(filters, search);
    }

    @ApiOperation(value = "Retrieve a medication by id.", authorizations = { @Authorization(value = "apiKey") })
    @GetMapping(path = MedicationConstants.MAPPING_GET_BY_ID)
    @Override
    public ResponseEntity<MedicationResource> findById(@PathVariable Integer id) {
        return super.findById(id);
    }

    @ApiOperation(value = "List medications by pagination.", authorizations = { @Authorization(value = "apiKey") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", dataType = "CustomParamPageable", paramType = "query", value = CoreConstants.CUSTOM_PAGEABLE_SWAGGER_FILTERS_SORTS_SEARCH_OPTIONS, example = CoreConstants.CUSTOM_PAGEABLE_SWAGGER_EXAMPLE) })
    @GetMapping
    @Override
    public ResponseEntity<PagedModel<MedicationResource>> findAllPaging(HttpServletRequest request) throws DroneException {
        return super.findAllPaging(request);
    }

    @ApiOperation(value = "Create a medication.", authorizations = { @Authorization(value = "apiKey") })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<MedicationResource> save(@RequestBody MedicationDTO dto) throws DroneException {
        return super.save(dto);
    }

    @ApiOperation(value = "Update a medication.", authorizations = { @Authorization(value = "apiKey") })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<MedicationResource> update(@RequestBody MedicationDTO dto) throws DroneException {
        return super.update(dto);
    }

    @ApiOperation(value = "Delete a medication.", authorizations = { @Authorization(value = "apiKey") })
    @DeleteMapping(path = MedicationConstants.MAPPING_GET_BY_ID)
    @Override
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) throws DroneException {
        return super.delete(id);
    }
}
