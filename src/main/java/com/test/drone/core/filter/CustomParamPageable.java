package com.test.drone.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.drone.core.base.CoreConstants;
import com.test.drone.core.exception.DroneException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@XmlRootElement(name = "data")
public class CustomParamPageable implements Serializable {

    @Getter
    private List<SearchCriteria> filters;

    private InternalSerializePage pageable;

    @Getter
    private String search;

    @Getter
    private String idProperty;

    @Getter
    private Boolean physical;

    private Class entity;

    private Integer pageSize;

    private Sort sorts;

    public CustomParamPageable() {
        this.physical = false;
        this.search = Strings.EMPTY;
        this.pageSize = CoreConstants.CUSTOM_PARAM_PAGEABLE_DEFAULT_PAGE_SIZE;
        this.sorts = null;
        this.filters = new ArrayList<>();
    }

    public Pageable getPageable() throws DroneException {
        try {
            return PageRequest.of(Objects.nonNull(pageable) ? pageable.getPage() : 0,
                    Objects.nonNull(pageable) ? pageable.size : pageSize,
                    (Objects.nonNull(pageable) && Objects.nonNull(pageable.orders) && !pageable.orders.isEmpty())
                            ? pageable.resolveSortObject(entity, physical)
                            : getSorts());
        } catch (NoSuchFieldException ex) {
            throw new DroneException(HttpStatus.NOT_FOUND, "default.error.pageable.field");
        }
    }

    public static CustomParamPageable mapRequestData(HttpServletRequest request) throws DroneException {
        CustomParamPageable data = new CustomParamPageable();
        if (Objects.nonNull(request.getParameter(CoreConstants.CUSTOM_PARAM_PAGEABLE_DATA))) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                data = mapper.readValue(request.getParameter(CoreConstants.CUSTOM_PARAM_PAGEABLE_DATA),
                        CustomParamPageable.class);
            } catch (IOException e) {
                throw new DroneException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
            }
        }

        return data;
    }

    /**
     * @return {@code Sort}
     */
    private Sort getSorts() {
        if (Objects.nonNull(sorts))
            return this.sorts;

        if (Objects.nonNull(idProperty))
            return Sort.by(Sort.Direction.DESC, idProperty);

        return Sort.unsorted();
    }

    @Setter
    static class InternalSerializePage implements Serializable {

        private int page;

        private int size;

        private transient List<CustomOrder> orders;

        public InternalSerializePage() {
            super();
        }

        private Sort resolveSortObject(Class entity, Boolean physical) throws NoSuchFieldException {
            List<Sort.Order> orderList = new ArrayList<>();
            String property;
            boolean flag = (Objects.nonNull(entity) && physical);
            for (CustomOrder order : this.orders) {
                property = order.property;
                if (flag) {
                    Field field = entity.getDeclaredField(property);
                    Column column = field.getAnnotation(Column.class);
                    property = column.name();
                }
                orderList.add(new Sort.Order(order.direction, property));
            }

            return Sort.by(orderList);
        }

        public int getPage() {
            return page != 0 ? page - 1 : 0;
        }

    }

    @Setter
    static class CustomOrder {

        private Sort.Direction direction;

        private String property;

        public CustomOrder() {
            super();
        }
    }
}
