package com.mrminnthitoo.notes_backend.API;

import lombok.Data;

@Data
public class RESTResponser {

    String message;
    ErrorCodes errorCode;
    Object errors;
    Object data;

}
