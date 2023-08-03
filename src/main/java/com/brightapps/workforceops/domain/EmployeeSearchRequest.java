package com.brightapps.workforceops.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeSearchRequest {
    @NotNull
    private String interest;
    @Min(0)
    private Integer minSalary;
}
