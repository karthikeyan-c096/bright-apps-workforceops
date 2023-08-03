package com.brightapps.workforceops.error;

import com.brightapps.workforceops.domain.ApiError;
import com.brightapps.workforceops.exception.EmployeesNotFoundException;
import com.brightapps.workforceops.exception.EmployeesSearchFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestResponseEntityExceptionHandler {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmployeesNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(final EmployeesNotFoundException ex) {
        log.error("500 Status Code", ex);
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, "Employees not found", ex));
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmployeesSearchFailedException.class)
    public ResponseEntity<Object> handleSearchFailed(final EmployeesSearchFailedException ex) {
        log.error("500 Status Code", ex);
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, "Something went wrong!", ex));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleValidationExceptions(MissingServletRequestParameterException ex) {
        log.error("400 Status Code", ex);
        ApiError error = new ApiError(BAD_REQUEST, "Validation failed", ex);
        return buildResponseEntity(error);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("400 Status Code", ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
