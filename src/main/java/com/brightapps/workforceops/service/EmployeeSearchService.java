package com.brightapps.workforceops.service;

import com.brightapps.workforceops.entity.Employee;

import java.util.List;

/**
 * @author Carlos Rucker
 */
public interface EmployeeSearchService {
    List<Employee> searchEmployeesByInterestAndSalary(String interest, Long salary);
}
