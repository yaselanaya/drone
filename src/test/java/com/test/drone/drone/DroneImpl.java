package com.test.drone.drone;

import com.test.drone.base.BaseMapper;
import com.test.drone.base.FILTER_RESULT;
import com.test.drone.base.ICrudTest;
import com.test.drone.base.TestConstant;
import com.test.drone.base.TestUtil;
import com.test.drone.core.base.CoreConstants;
import com.test.drone.domain.drone.Drone_;
import com.test.drone.domain.drone.Model;
import com.test.drone.domain.drone.State;
import com.test.drone.web.drone.DroneController;
import com.test.drone.web.drone.dto.DroneDTO;
import com.test.drone.web.drone.dto.DroneResource;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Component
public class DroneImpl implements ICrudTest {

    private final DroneController controller;

    private final Class<DroneController> controllerClass;

    private final BaseMapper baseMapper;

    private final MockMvc mockMvc;

    private final DroneFilter filter;

    @SuppressWarnings("unchecked")
    public DroneImpl(DroneController controller, BaseMapper baseMapper, WebApplicationContext webApplicationContext,
                     DroneFilter filter) {
        this.controller = controller;
        this.controllerClass = (Class<DroneController>) controller.getClass();
        this.baseMapper = baseMapper;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.filter = filter;
    }

    @Override
    public void findById() throws Exception {
        Method method = TestUtil.getMethod(controllerClass, TestConstant.ENDPOINT_FIND_BY_ID);

        GetMapping annotation = method.getAnnotation(GetMapping.class);

        Integer requestId = NumberUtils.INTEGER_ONE;
        String endpoint = TestUtil.buildBasicEndpointUrl(annotation.path(), controller.getClass())
                .replace("{id}/", requestId.toString());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(endpoint).contentType(MediaType.APPLICATION_JSON);

        // WHEN
        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        DroneResource resource = buildResource(result.getResponse().getContentAsString());

        //THEN
        notNull(resource, "Resource retrieve should not be null");
        isTrue(resource.getId().equals(requestId), "The resource retrieve is not valid");
    }

    @Override
    public void findAllPaging() throws Exception {
        Method method = TestUtil.getMethod(controllerClass, TestConstant.ENDPOINT_FIND_ALL_PAGING);

        GetMapping annotation = method.getAnnotation(GetMapping.class);

        String endpoint = TestUtil.buildBasicEndpointUrl(annotation.path(), controller.getClass());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(endpoint).contentType(MediaType.APPLICATION_JSON)
                .param(CoreConstants.CUSTOM_PARAM_PAGEABLE_DATA, filter.filter(FILTER_RESULT.ALL));

        // WHEN
        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        Collection<DroneResource> resources = toResourceCollection(result.getResponse().getContentAsByteArray());

        //THEN
        notNull(resources, "The resource list should not be null");
        Assert.notEmpty(resources, "The resource list should not be empty");

        mockHttpServletRequestBuilder = get(endpoint).contentType(MediaType.APPLICATION_JSON)
                .param(CoreConstants.CUSTOM_PARAM_PAGEABLE_DATA, filter.filter(FILTER_RESULT.ANY));

        // WHEN
        result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        resources = toResourceCollection(result.getResponse().getContentAsByteArray());

        //THEN
        notNull(resources, "The resource list should not be null");
        isTrue(NumberUtils.INTEGER_ONE.equals(resources.size()), "The resource list should have only one item");

        mockHttpServletRequestBuilder = get(endpoint).contentType(MediaType.APPLICATION_JSON)
                .param(CoreConstants.CUSTOM_PARAM_PAGEABLE_DATA, filter.filter(FILTER_RESULT.SINGLE));

        // WHEN
        result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        resources = toResourceCollection(result.getResponse().getContentAsByteArray());

        //THEN
        notNull(resources, "The resource list should not be null");
        isTrue(NumberUtils.INTEGER_ONE.equals(resources.size()), "The resource list should have only one item");

        mockHttpServletRequestBuilder = get(endpoint).contentType(MediaType.APPLICATION_JSON)
                .param(CoreConstants.CUSTOM_PARAM_PAGEABLE_DATA, filter.filter(FILTER_RESULT.NONE));

        // WHEN
        result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        resources = toResourceCollection(result.getResponse().getContentAsByteArray());

        //THEN
        notNull(resources, "The resources list should not be null");
        isTrue(NumberUtils.INTEGER_ZERO.equals(resources.size()), "The resources list should be empty");
    }

    @Override
    public void save() throws Exception {
        Method method = TestUtil.getMethod(controllerClass, TestConstant.ENDPOINT_SAVE);

        PostMapping annotation = method.getAnnotation(PostMapping.class);

        String endpoint = TestUtil.buildBasicEndpointUrl(annotation.path(), controller.getClass());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(endpoint).contentType(MediaType.APPLICATION_JSON)
                .content(baseMapper.getObjectMapper().writeValueAsString(buildDto(true)));

        // WHEN
        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        DroneResource resource = buildResource(result.getResponse().getContentAsString());

        //THEN
        notNull(resource, "The saved resource should not be null");
        notNull(resource.getId(), "The saved resource must have an Id assigned");
    }

    @Override
    public void update() throws Exception {
        Method method = TestUtil.getMethod(controllerClass, TestConstant.ENDPOINT_UPDATE);

        PutMapping annotation = method.getAnnotation(PutMapping.class);

        String endpoint = TestUtil.buildBasicEndpointUrl(annotation.path(), controller.getClass());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(endpoint).contentType(MediaType.APPLICATION_JSON)
                .content(baseMapper.getObjectMapper().writeValueAsString(buildDto(false)));

        // WHEN
        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        DroneResource resource = buildResource(result.getResponse().getContentAsString());

        //THEN
        notNull(resource, "The updated resource should not be null");
        notNull(resource.getId(), "The updated resource must have an Id assigned");
    }

    private DroneDTO buildDto(boolean isSaveAction) {
        return DroneDTO.builder()
                .id(isSaveAction ? null : NumberUtils.INTEGER_ONE)
                .batteryCapacity(Short.valueOf("100"))
                .model(Model.HEAVY_WEIGHT)
                .serialNumber("Drone 1")
                .state(State.LOADING)
                .weightLimit(Short.valueOf("500"))
                .build();
    }

    @SuppressWarnings("unchecked")
    protected Collection<DroneResource> toResourceCollection(byte[] content) throws IOException {
        Map<String, Collection<Map<String, Object>>> responseEntity = baseMapper.getObjectMapper().readValue(content, Map.class);
        Collection<Map<String, Object>> propertyMaps = responseEntity.getOrDefault(
                TestConstant.PAGE_IMPL_CONTENT,
                Lists.newArrayList()
        );

        return propertyMaps.stream().map(this::fromPropertyMap).collect(Collectors.toList());
    }

    private DroneResource buildResource(String content) throws IOException {
        return baseMapper.getObjectMapper().readValue(content, DroneResource.class);
    }

    private DroneResource fromPropertyMap(Map<String, Object> properties) {
        return DroneResource.builder()
                .id((Integer) properties.getOrDefault(Drone_.ID, null))
                .serialNumber((String) properties.getOrDefault(Drone_.SERIAL_NUMBER, Strings.EMPTY))
                .model(Optional.ofNullable(properties.get(Drone_.MODEL))
                        .map(model -> Model.getModelByName((String) model))
                        .orElse(null))
                .weightLimit(Optional.ofNullable((Number) properties.get(Drone_.WEIGHT_LIMIT))
                                .map(Number::shortValue).orElse(null))
                .batteryCapacity(Optional.ofNullable((Number) properties.get(Drone_.BATTERY_CAPACITY))
                        .map(Number::shortValue).orElse(null))
                .state(Optional.ofNullable(properties.get(Drone_.STATE))
                        .map(state -> State.getStateByName((String) state))
                        .orElse(null))
                .build();
    }
}
