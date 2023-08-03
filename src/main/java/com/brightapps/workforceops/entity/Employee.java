package com.brightapps.workforceops.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Carlos Rucker
 */
@Data
public class Employee {
    private String firstName;

    private String lastName;

    private String designation;

    private Long salary;

    private String dateOfJoining;

    private String address;

    private String gender;

    private Integer age;

    private String maritalStatus;

    private List<String> interests;
}
