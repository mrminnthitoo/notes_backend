package com.mrminnthitoo.notes_backend.API;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class APIResponse {

    public ResponseEntity<RESTResponser> successResponse(HttpStatus statusCode, String message, Object data){
        RESTResponser restResponse = new RESTResponser();
        restResponse.message = message;
        restResponse.data = data;
        return ResponseEntity.status(statusCode).body(restResponse);
    }

    public ResponseEntity<RESTResponser> errorResponse(HttpStatus statusCode, String message, ErrorCodes errorCode, Object error){
        RESTResponser restResponse = new RESTResponser();
        restResponse.setMessage(message);
        restResponse.setErrorCode(errorCode);
        restResponse.setErrors(error);
        return ResponseEntity.status(statusCode).body(restResponse);
    }

}
