package com.mrminnthitoo.notes_backend.API;

import com.mrminnthitoo.notes_backend.exceptions.NotFoundException;
import com.mrminnthitoo.notes_backend.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class APIAdvice {

    @Autowired
    private APIResponse apiResponse;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RESTResponser> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        Map<String, String> error = new HashMap<>();
        error.put(exception.getName(), exception.getName() + " should be type of " + Objects.requireNonNull(exception.getRequiredType()).getName());
        return this.apiResponse.errorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid Type.",
                ErrorCodes.TYPE_ERROR,
                error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RESTResponser> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        String error = exception.getMessage();
        return this.apiResponse.errorResponse(
                HttpStatus.BAD_REQUEST,
                "request body must be json format.",
                ErrorCodes.FORMAT_ERROR,
                error);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<RESTResponser> handleMissingPathVariableException(MissingPathVariableException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put(exception.getVariableName(), exception.getMessage());
        return this.apiResponse.errorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                ErrorCodes.NOT_PRESENT,
                errors
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RESTResponser> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put(exception.getParameterName(), exception.getMessage());
        return this.apiResponse.errorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                ErrorCodes.NOT_PRESENT,
                errors
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RESTResponser> handleNotFoundException(NotFoundException exception){
        return this.apiResponse.errorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                ErrorCodes.NOT_FOUND,
                exception.getErrors());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RESTResponser> handleValidationException(ValidationException exception){
        return this.apiResponse.errorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                ErrorCodes.VALIDATION_FAIL,
                exception.getErrors());
    }

}
