package com.mrminnthitoo.notes_backend.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    @NotNull(message = "{user.name.required}")
    @NotBlank(message = "{user.name.required}")
    @Size(min = 3, max = 50, message = "{user.name.size}")
    private String name;

    @NotNull(message = "{user.email.required}")
    @NotBlank(message = "{user.email.required}")
    @Size(min = 5, max = 100, message = "{user.email.size}")
    private String email;

    @NotNull(message = "{user.password.required}")
    @NotBlank(message = "{user.password.required}")
    @Size(min = 6, max = 100, message = "{user.password.size}")
    private String password;

}
