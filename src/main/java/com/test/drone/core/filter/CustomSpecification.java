package com.test.drone.core.filter;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class CustomSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return buildPredicate(root, builder);
    }

    @SuppressWarnings("unchecked assignment")
    public Predicate buildPredicate(From root, CriteriaBuilder builder) {
        switch (criteria.getOperator()) {
            case EQUALITY:
                return builder.equal(root.get(criteria.getField()), criteria.getValue());
            case NEGATION:
                return builder.notEqual(root.get(criteria.getField()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(root.<String> get(criteria.getField()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.<String> get(criteria.getField()), criteria.getValue().toString());
            case GREATER_THAN_EQUAL:
                return builder.greaterThanOrEqualTo(root.<String> get(criteria.getField()), criteria.getValue().toString());
            case LESS_THAN_EQUAL:
                return builder.lessThanOrEqualTo(root.<String> get(criteria.getField()), criteria.getValue().toString());
            case LIKE:
                return builder.like(builder.upper(root.<String> get(criteria.getField())),
                        "%" + criteria.getValue().toString().toUpperCase() + "%");
            case STARTS_WITH:
                return builder.like(root.<String> get(criteria.getField()), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(root.<String> get(criteria.getField()), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(root.<String> get(criteria.getField()), "%" + criteria.getValue() + "%");
            case NUMBER_LESS_EQUAL_THAN:
                return builder.le(root.get(criteria.getField()), (Number) criteria.getValue());
            case NUMBER_GREATER_EQUAL_THAN:
                return builder.ge(root.get(criteria.getField()), (Number) criteria.getValue());
            case COMPARABLE_LESS_EQUAL_THAN:
                return builder.lessThanOrEqualTo(root.get(criteria.getField()), (Comparable) criteria.getValue());
            case COMPARABLE_GREATER_EQUAL_THAN:
                return builder.greaterThanOrEqualTo(root.get(criteria.getField()), (Comparable) criteria.getValue());
            case DATE_LESS_EQUAL_THAN:
                return builder.lessThanOrEqualTo(root.get(criteria.getField()),
                        LocalDateTime.parse(criteria.getValue().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            case DATE_GREATER_EQUAL_THAN:
                return builder.greaterThanOrEqualTo(root.get(criteria.getField()),
                        LocalDateTime.parse(criteria.getValue().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            case IN:
                return builder.in(root.get(criteria.getField())).value(criteria.getValue());
            case NOT_IN:
                return builder.not(builder.in(root.get(criteria.getField())).value(criteria.getValue()));
            case IS_NULL:
                return builder.isNull(root.get(criteria.getField()));
            case NOT_NULL:
                return builder.isNotNull(root.get(criteria.getField()));
            case IS_EMPTY:
                return builder.equal(root.get(criteria.getField()), Strings.EMPTY);
            case IS_NOT_EMPTY:
                return builder.notEqual(root.get(criteria.getField()), Strings.EMPTY);
            case IS_NULL_OR_ZERO:
                return builder.or(builder.isNull(root.get(criteria.getField())),
                        builder.equal(root.get(criteria.getField()), NumberUtils.INTEGER_ZERO));
            case IS_ZERO:
                return builder.equal(root.get(criteria.getField()), NumberUtils.INTEGER_ZERO);
            case IS_NULL_OR_EMPTY:
                return builder.or(builder.isNull(root.get(criteria.getField())),
                        builder.equal(root.get(criteria.getField()), Strings.EMPTY));
            default:
                return null;
        }
    }
}
