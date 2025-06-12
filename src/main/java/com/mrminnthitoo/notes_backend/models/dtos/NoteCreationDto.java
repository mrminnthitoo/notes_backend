package com.mrminnthitoo.notes_backend.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteCreationDto {

    @Size(max = 200, message = "{note.title.size}")
    private String title;

    @NotNull(message = "{note.content.required}")
    @NotBlank(message = "{note.content.required}")
    @Size(min = 10, max = 10000, message = "{note.content.size}")
    private String content;

}
