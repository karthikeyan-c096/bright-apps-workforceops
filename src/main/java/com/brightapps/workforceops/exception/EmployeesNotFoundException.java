package com.brightapps.workforceops.exception;

public class EmployeesNotFoundException extends Exception {
    public EmployeesNotFoundException(String searchQuery) {
        super(String.format("Employees not found for the search term : %s", searchQuery));
    }
}
