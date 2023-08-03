package com.brightapps.workforceops.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Carlos Rucker
 */
@Data
@Document(indexName = "employees")
public class Employee {
    private String firstName;

    private String lastName;

    private String designation;

    private Long salary;

    @Field(type = FieldType.Date, format = DateFormat.date, pattern = "yyyy-MM-dd")
    private LocalDate dateOfJoining;

    private String address;

    private String gender;

    private Integer age;

    private String maritalStatus;

    private List<String> interests;
}
