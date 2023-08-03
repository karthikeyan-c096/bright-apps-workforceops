package com.brightapps.workforceops.service;

import com.brightapps.workforceops.domain.EmployeeSearchRequest;
import com.brightapps.workforceops.domain.EmployeeSearchResult;
import com.brightapps.workforceops.exception.EmployeesNotFoundException;
import com.brightapps.workforceops.exception.EmployeesSearchFailedException;
import org.springframework.data.domain.Pageable;

/**
 * @author Carlos Rucker
 */
public interface EmployeeSearchService {
    EmployeeSearchResult searchEmployeesByInterestAndSalary(EmployeeSearchRequest request, Pageable page) throws EmployeesNotFoundException, EmployeesSearchFailedException;
}
