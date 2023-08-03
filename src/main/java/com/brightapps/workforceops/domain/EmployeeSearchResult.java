package com.brightapps.workforceops.domain;

import com.brightapps.workforceops.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeSearchResult {
    private Long count;
    private List<Employee> results;
}
