package com.mrminnthitoo.notes_backend.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ValidationException extends Exception{

    private Map<String, String> errors = new HashMap<>();

    public ValidationException(String message, Map<String, String> errors){
        super(message);
        this.errors = errors;
    }

    public ValidationException(String message, String key, String error){
        super(message);
        this.errors.put(key, error);
    }

    public ValidationException(String message, List<FieldError> fieldErrors){
        super(message);
        for (FieldError fieldError : fieldErrors){
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
