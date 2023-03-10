package com.test.drone.web.drone;

import com.test.drone.core.base.BaseController;
import com.test.drone.core.base.CoreConstants;
import com.test.drone.core.base.IEntitySpecification;
import com.test.drone.core.base.IMessages;
import com.test.drone.core.exception.DroneException;
import com.test.drone.core.filter.CustomParamPageable;
import com.test.drone.core.filter.SearchCriteria;
import com.test.drone.core.filter.SearchOperation;
import com.test.drone.domain.drone.Drone;
import com.test.drone.domain.drone.DroneSpecification;
import com.test.drone.domain.drone.Drone_;
import com.test.drone.domain.drone.IDroneService;
import com.test.drone.domain.drone.State;
import com.test.drone.infraestructure.drone.DroneConstants;
import com.test.drone.infraestructure.drone.DroneFactory;
import com.test.drone.web.drone.dto.DroneDTO;
import com.test.drone.web.drone.dto.DroneResource;
import com.test.drone.web.drone.dto.DroneResourceAssembler;
import com.test.drone.web.medication.dto.LoadDroneDTO;
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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = DroneConstants.BASE_PATH)
@Api(tags = {"Drone Controller"})
@SwaggerDefinition(tags = {@Tag(name = "Drone Controller", description = "Operations pertaining to drones")})
public class DroneController extends BaseController<Drone, DroneDTO, Integer, DroneResource, DroneFactory, DroneController, IDroneService, DroneResourceAssembler> {

    private final MedicationResourceAssembler medicationAssembler;

    protected DroneController(IDroneService service, DroneResourceAssembler assembler, PagedResourcesAssembler<Drone> pagedResourcesAssembler, IMessages messages, MedicationResourceAssembler medicationAssembler) {
        super(service, assembler, pagedResourcesAssembler, messages);
        this.medicationAssembler = medicationAssembler;
    }

    @Override
    protected String getIdField() {
        return Drone_.ID;
    }

    @Override
    protected IEntitySpecification<Drone> getSpecification(List<SearchCriteria> filters, String search) {
        return new DroneSpecification(filters, search);
    }

    @ApiOperation(value = "Retrieve a drone by id.", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(path = DroneConstants.MAPPING_GET_BY_ID)
    @Override
    public ResponseEntity<DroneResource> findById(@PathVariable Integer id) {
        return super.findById(id);
    }

    @ApiOperation(value = "List drones by pagination.", authorizations = {@Authorization(value = "apiKey")})
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "data",
                    dataType = "CustomParamPageable",
                    paramType = "query",
                    value = CoreConstants.CUSTOM_PAGEABLE_SWAGGER_FILTERS_SORTS_SEARCH_OPTIONS,
                    example = CoreConstants.CUSTOM_PAGEABLE_SWAGGER_EXAMPLE
            )
    })
    @GetMapping
    @Override
    public ResponseEntity<PagedModel<DroneResource>> findAllPaging(HttpServletRequest request) throws DroneException {
        return super.findAllPaging(request);
    }

    @ApiOperation(value = "Create a drone.", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<DroneResource> save(@RequestBody DroneDTO dto) throws DroneException {
        return super.save(dto);
    }

    @ApiOperation(value = "Update a drone.", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<DroneResource> update(@RequestBody DroneDTO dto) throws DroneException {
        return super.update(dto);
    }

    @ApiOperation(value = "Delete a drone.", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(path = DroneConstants.MAPPING_GET_BY_ID)
    @Override
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) throws DroneException {
        return super.delete(id);
    }

    @ApiOperation(value = "Load a drone with medications.", authorizations = {@Authorization(value = "apiKey")})
    @PatchMapping(path = DroneConstants.MAPPING_LOAD_DRONE)
    public ResponseEntity<HttpStatus> loadMedications(@RequestBody LoadDroneDTO loadDroneDTO) throws DroneException {

        Integer droneId = loadDroneDTO.getId();
        if (Objects.isNull(droneId)) {
            throw new DroneException(
                    HttpStatus.BAD_REQUEST,
                    messages.getMessage("validation.error.drone.notnull.id")
            );
        }

        Set<Integer> medications = loadDroneDTO.getMedications();
        if (Objects.isNull(medications) || medications.isEmpty()) {
            throw new DroneException(
                    HttpStatus.BAD_REQUEST,
                    messages.getMessage("validation.error.drone.load.medication.null.empty")
            );
        }

        service.loadMedications(droneId, medications);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation(value = "List the medications by drone.", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(path = DroneConstants.MAPPING_GET_MEDICATIONS_LOADED)
    public ResponseEntity<Collection<MedicationResource>> getMedicationsByDrone(@PathVariable Integer id) throws DroneException {
        return ResponseEntity.ok(medicationAssembler.toModels(service.getMedicationsByDrone(id)));
    }

    @ApiOperation(value = "List available drones.", authorizations = {@Authorization(value = "apiKey")})
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "data",
                    dataType = "CustomParamPageable",
                    paramType = "query",
                    value = CoreConstants.CUSTOM_PAGEABLE_SWAGGER_FILTERS_SORTS_SEARCH_OPTIONS,
                    example = CoreConstants.CUSTOM_PAGEABLE_SWAGGER_EXAMPLE
            )
    })
    @GetMapping(value = DroneConstants.MAPPING_GET_AVAILABLE_DRONES)
    public ResponseEntity<Collection<DroneResource>> listAvailableDrone(HttpServletRequest request) throws DroneException {

        CustomParamPageable data = addAvailableStateFilter(CustomParamPageable.mapRequestData(request));

        Collection<Drone> availableDrones = service.findAll(
                new DroneSpecification(data.getFilters()).toSpecification(),
                data.getPageable()
        ).stream().collect(Collectors.toList());

        return ResponseEntity.ok(assembler.toModels(availableDrones));
    }

    @ApiOperation(value = "Get the battery capacity.", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = DroneConstants.MAPPING_GET_BATTERY_CAPACITY)
    public ResponseEntity<Short> listAvailableDrone(@PathVariable Integer id) throws DroneException {
        Drone drone = service.findById(id).orElseThrow(() -> new DroneException(
                HttpStatus.NOT_FOUND,
                messages.getMessage("validation.error.drone.doesnt.exit")
        ));

        return ResponseEntity.ok(drone.getBatteryCapacity());
    }

    private CustomParamPageable addAvailableStateFilter(CustomParamPageable data) {
        List<SearchCriteria> filters = data.getFilters();

        SearchCriteria stateFilter = filters
                .stream()
                .filter(filter -> filter.getField().equals(Drone_.STATE))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(stateFilter)) {
            filters.remove(stateFilter);
        }

        filters.add(
                SearchCriteria.builder()
                        .field(Drone_.STATE)
                        .operator(SearchOperation.EQUALITY)
                        .value(State.IDLE.name())
                        .build()
        );

        data.setFilters(filters);
        return data;
    }
}
