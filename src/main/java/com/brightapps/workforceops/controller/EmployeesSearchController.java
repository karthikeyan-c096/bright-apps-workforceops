package com.brightapps.workforceops.controller;

import com.brightapps.workforceops.domain.EmployeeSearchRequest;
import com.brightapps.workforceops.domain.EmployeeSearchResult;
import com.brightapps.workforceops.exception.EmployeesNotFoundException;
import com.brightapps.workforceops.exception.EmployeesSearchFailedException;
import com.brightapps.workforceops.service.EmployeeSearchService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<EmployeeSearchResult> findEmployeesByInterestAndSalary(@Valid @RequestBody EmployeeSearchRequest request, @RequestParam(required = false, defaultValue = "1") Integer page) throws EmployeesNotFoundException, EmployeesSearchFailedException {
        log.info("searching by interest '{}', minimum salary '{}'. page-{}", request.getInterest(), request.getMinSalary(), page);
        int start = 0;
        if (page > 1) {
            start = page - 1;
        }
        Pageable pageable = PageRequest.of(start, 10);
        EmployeeSearchResult result = employeeSearchService.searchEmployeesByInterestAndSalary(request, pageable);
        return ResponseEntity.ok(result);
    }
}
