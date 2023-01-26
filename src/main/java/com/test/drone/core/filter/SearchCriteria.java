package com.test.drone.core.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;

@Data
public class SearchCriteria {

    private String field;

    private SearchOperation operator;

    private Object value;

    private LogicalOperator logicalOperator;

    public SearchCriteria() {
        this.logicalOperator = LogicalOperator.AND;
    }

    @Builder
    private SearchCriteria(String field, SearchOperation operator, Object value, LogicalOperator logicalOperator) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.logicalOperator = Optional.ofNullable(logicalOperator).orElse(LogicalOperator.AND);
    }

    @JsonIgnore
    public boolean isValid() {
        return (Objects.nonNull(field) && !field.isEmpty()) && (Objects.nonNull(value) && !value.toString().isEmpty());
    }
}
