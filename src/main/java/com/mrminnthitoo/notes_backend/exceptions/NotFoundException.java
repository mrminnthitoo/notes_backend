package com.mrminnthitoo.notes_backend.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class NotFoundException extends Exception{

    private Map<String, String> errors = new HashMap<>();

    public NotFoundException(String message, Map<String, String> errors){
        super(message);
        this.errors = errors;
    }

    public NotFoundException(String message, String key, String error){
        super(message);
        this.errors.put(key, error);
    }

}
