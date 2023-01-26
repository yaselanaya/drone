package com.test.drone.medication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.drone.base.BaseMapper;
import com.test.drone.base.FILTER_RESULT;
import com.test.drone.base.ICrudTest;
import com.test.drone.base.TestConstant;
import com.test.drone.base.TestUtil;
import com.test.drone.core.base.CoreConstants;
import com.test.drone.domain.medication.Medication;
import com.test.drone.domain.medication.Medication_;
import com.test.drone.web.medication.MedicationController;
import com.test.drone.web.medication.dto.MedicationDTO;
import com.test.drone.web.medication.dto.MedicationResource;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Component
public class MedicationImpl implements ICrudTest {

    private final MedicationController controller;

    private final Class<MedicationController> controllerClass;

    private final BaseMapper baseMapper;

    private final MockMvc mockMvc;

    private final MedicationFilter filter;

    @SuppressWarnings("unchecked")
    public MedicationImpl(MedicationController controller, BaseMapper baseMapper,
                          WebApplicationContext webApplicationContext, MedicationFilter filter) {
        this.controller = controller;
        this.controllerClass = (Class<MedicationController>) controller.getClass();
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
        MedicationResource resource = buildResource(result.getResponse().getContentAsString());

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
        Collection<MedicationResource> resources = toResourceCollection(result.getResponse().getContentAsByteArray());

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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .multipart(endpoint)
                .file(getImage(true));

        // WHEN
        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        MedicationResource resource = buildResource(result.getResponse().getContentAsString());

        //THEN
        notNull(resource, "The saved resource should not be null");
        notNull(resource.getId(), "The saved resource must have an Id assigned");
    }

    @Override
    public void update() throws Exception {
        Method method = TestUtil.getMethod(controllerClass, TestConstant.ENDPOINT_UPDATE);
        PutMapping annotation = method.getAnnotation(PutMapping.class);

        String endpoint = TestUtil.buildBasicEndpointUrl(annotation.path(), controller.getClass());
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .multipart(endpoint)
                .file(getImage(false));

        // WHEN
        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        // EXPECT
        MedicationResource resource = buildResource(result.getResponse().getContentAsString());

        //THEN
        notNull(resource, "The updated resource should not be null");
        notNull(resource.getId(), "The updated resource must have an Id assigned");
    }

    private Medication buildEntity(Integer droneId) {
        return Medication.builder()
                .id(droneId)
                .code("CODE_1")
                .image("uploads/ebd20bb6-ca2f-49cc-bbdd-d248ccb8043b-slack.jpeg")
                .name("MED_1")
                .weight(Short.valueOf("50"))
                .build();
    }

    private MedicationDTO buildDto(boolean isSaveAction) {
        return MedicationDTO.builder()
                .id(isSaveAction ? null : NumberUtils.INTEGER_ONE)
                .code("CODE_1")
                .image("uploads/ebd20bb6-ca2f-49cc-bbdd-d248ccb8043b-slack.jpeg")
                .name("MED_1")
                .weight(Short.valueOf("50"))
                .build();
    }

    @SuppressWarnings("unchecked")
    protected Collection<MedicationResource> toResourceCollection(byte[] content) throws IOException {
        Map<String, Collection<Map<String, Object>>> responseEntity = baseMapper.getObjectMapper().readValue(content, Map.class);
        Collection<Map<String, Object>> propertyMaps = responseEntity.getOrDefault(
                TestConstant.PAGE_IMPL_CONTENT,
                Lists.newArrayList()
        );

        return propertyMaps.stream().map(this::fromPropertyMap).collect(Collectors.toList());
    }

    private MedicationResource buildResource(String content) throws IOException {
        return baseMapper.getObjectMapper().readValue(content, MedicationResource.class);
    }

    private MockMultipartFile getImage(boolean isSaveAction) throws JsonProcessingException {
        return  new MockMultipartFile(
                "data",
                "image",
                MediaType.APPLICATION_JSON_VALUE,
                baseMapper.getObjectMapper().writeValueAsString(buildDto(isSaveAction)).getBytes()
        );
    }

    private MedicationResource fromPropertyMap(Map<String, Object> properties) {
        return MedicationResource.builder()
                .id((Integer) properties.getOrDefault(Medication_.ID, null))
                .name((String) properties.getOrDefault(Medication_.NAME, Strings.EMPTY))
                .weight(Optional.ofNullable((Number) properties.get(Medication_.WEIGHT))
                        .map(Number::shortValue).orElse(null))
                .code((String) properties.getOrDefault(Medication_.CODE, Strings.EMPTY))
                .image((String) properties.getOrDefault(Medication_.IMAGE, Strings.EMPTY))
                .build();
    }
}
