package com.brightapps.workforceops.service.impl;

import com.brightapps.workforceops.entity.Employee;
import com.brightapps.workforceops.service.EmployeeSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Carlos Rucker
 */
@Service
@Slf4j
public class EmployeeSearchServiceImpl implements EmployeeSearchService {

    private static final String PRODUCT_INDEX = "employees";
    private final ElasticsearchOperations elasticsearchOperations;

    public EmployeeSearchServiceImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * Locates employees in Elasticsearch with salaries higher than the given amount and meets the given interest criteria.
     * @param interest
     * @param salary
     * @return list of employees
     */
    @Override
    public List<Employee> searchEmployeesByInterestAndSalary(String interest, Long salary) {

        // 1. Create query on interests and salary fields enabling fuzzy search
        Criteria criteria = new Criteria("interests").is(interest)
                .and("salary").greaterThan(salary);
        Query query = new CriteriaQuery(criteria);

        // 2. Execute search
        SearchHits<Employee> searchHits = elasticsearchOperations
                .search(query, Employee.class, IndexCoordinates.of(PRODUCT_INDEX));

        log.info("Employee search results. Count {}", searchHits.getSearchHits().size());

        // 3. Map searchHits to employee list
        List<Employee> employees = new ArrayList<>();
        searchHits.forEach(searchHit -> {
            Employee employee = searchHit.getContent();
            employees.add(employee);
            log.info("Employee: {}", employee);
        });
        return employees;
    }
}
