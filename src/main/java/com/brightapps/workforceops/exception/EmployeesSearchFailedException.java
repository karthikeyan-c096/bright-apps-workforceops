package com.brightapps.workforceops.exception;

public class EmployeesSearchFailedException extends Exception {
    public EmployeesSearchFailedException(String searchQuery, Throwable throwable) {
        super(String.format("Employees search failed for the search term : %s", searchQuery), throwable);
    }
}
