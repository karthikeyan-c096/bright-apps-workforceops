package com.brightapps.workforceops.controller;

import com.brightapps.workforceops.entity.Employee;
import com.brightapps.workforceops.service.EmployeeSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Carlos Rucker
 */
@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeesSearchController {

    private final EmployeeSearchService employeeSearchService;

    public EmployeesSearchController(EmployeeSearchService employeeSearchService) {
        this.employeeSearchService = employeeSearchService;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Employee> findByEmployeesInterestAndSalary(@RequestParam String interest, @RequestParam Long salary) {
        log.info("searching by interest '{}', salary '{}'", interest, salary);
        return employeeSearchService.searchEmployeesByInterestAndSalary(interest, salary);
    }
}
