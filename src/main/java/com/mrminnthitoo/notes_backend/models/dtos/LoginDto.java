package com.mrminnthitoo.notes_backend.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private String username;
    private String email;
    private String password;

}
