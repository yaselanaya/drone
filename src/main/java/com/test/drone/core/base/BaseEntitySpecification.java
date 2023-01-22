package com.test.drone.core.base;

import com.test.drone.core.filter.CustomSpecification;
import com.test.drone.core.filter.SearchCriteria;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BaseEntitySpecification<Entity> implements IEntitySpecification<Entity> {

    protected List<SearchCriteria> filters;

    protected List<SearchCriteria> searchFilters;

    protected String search;

    public BaseEntitySpecification(List<SearchCriteria> filters, String search) {
        this.filters = filters;
        this.search = search;
        searchFilters = new ArrayList<>();
    }

    public BaseEntitySpecification(List<SearchCriteria> filters) {
        this(filters, Strings.EMPTY);
    }

    public BaseEntitySpecification(List<SearchCriteria> filters, List<SearchCriteria> searchFilters) {
        this(filters, Strings.EMPTY);
        this.searchFilters = searchFilters;
    }

    @Override
    public Specification<Entity> toSpecification() {

        Specification<Entity> filterResult = filters.stream().map(this::buildSpecification).filter(Objects::nonNull)
                .reduce(Specification::and).orElse(null);

        return search.isEmpty() && searchFilters.isEmpty() ? filterResult : processSearch(filterResult);
    }

    private Specification<Entity> processSearch(Specification<Entity> filterResult) {

        populateSearchCriteria();

        if (searchFilters.isEmpty())
            return filterResult;

        Specification<Entity> searchSpecification = searchFilters.stream().map(this::buildSpecification).filter(Objects::nonNull)
                .reduce(Specification::or).orElse(null);

        List<Specification<Entity>> validSearchSpecification = getSearchSpecification().stream().filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (Objects.nonNull(searchSpecification))
            for (Specification<Entity> specification : validSearchSpecification) {
                searchSpecification = searchSpecification.or(specification);
            }

        return Objects.isNull(filterResult) ? searchSpecification : filterResult.and(searchSpecification);
    }

    protected void populateSearchCriteria() {
        //Should be implemented in child classes
    }

    protected List<Specification<Entity>> getSearchSpecification() {
        return Collections.emptyList();
    }

    protected Specification<Entity> buildSpecification(SearchCriteria criteria) {
        return new CustomSpecification<>(criteria);
    }
}
