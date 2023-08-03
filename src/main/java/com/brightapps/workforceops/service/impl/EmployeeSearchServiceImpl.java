package com.brightapps.workforceops.service.impl;

import com.brightapps.workforceops.domain.EmployeeSearchRequest;
import com.brightapps.workforceops.domain.EmployeeSearchResult;
import com.brightapps.workforceops.entity.Employee;
import com.brightapps.workforceops.exception.EmployeesNotFoundException;
import com.brightapps.workforceops.exception.EmployeesSearchFailedException;
import com.brightapps.workforceops.service.EmployeeSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
     * @param interest employee's one of the interests
     * @param salary salary of the employee
     * @return list of employees
     * @throws EmployeesNotFoundException thrown when employees search result is zero
     * @throws EmployeesSearchFailedException thrown when employees search failed in elasticsearch
     */
    @Override
    public EmployeeSearchResult searchEmployeesByInterestAndSalary(EmployeeSearchRequest request, Pageable page) throws EmployeesNotFoundException, EmployeesSearchFailedException {

        String interest = request.getInterest();
        Integer minSalary = request.getMinSalary();

        try {
            // 1. Create query on interests and salary fields enabling fuzzy search
            Criteria criteria = new Criteria("interests").is(interest)
                    .and("salary").greaterThan(minSalary);
            Query query = new CriteriaQuery(criteria).setPageable(page);

            // 2. Execute search
            SearchHits<Employee> searchHits = elasticsearchOperations
                    .search(query, Employee.class, IndexCoordinates.of(PRODUCT_INDEX));

            log.info("Employee search results. Count {}", searchHits.getTotalHits());

            // 3. Map searchHits to employee list
            List<Employee> employees = new ArrayList<>();
            searchHits.forEach(searchHit -> {
                Employee employee = searchHit.getContent();
                employees.add(employee);
                log.info("Employee: {}", employee);
            });

            if (searchHits.getTotalHits() > 0) {
                return new EmployeeSearchResult(searchHits.getTotalHits(), employees);
            }
        } catch (Exception e) {
            throw new EmployeesSearchFailedException(String.format("interest '%s', minSalary '%s'", interest, minSalary), e);
        }

        throw new EmployeesNotFoundException(String.format("interest '%s', minSalary '%s'", interest, minSalary));
    }
}
