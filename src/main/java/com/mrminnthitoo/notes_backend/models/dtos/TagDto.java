package com.mrminnthitoo.notes_backend.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {

    private Long id;

    @NotNull(message = "{tag.name.required}")
    @NotBlank(message = "{tag.name.required}")
    @Size(message = "{tag.name.size}")
    private String name;

}
