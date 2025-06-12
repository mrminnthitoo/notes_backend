package com.mrminnthitoo.notes_backend.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagNotesDto {

    private Long id;
    private String name;
    private List<NoteDto> notes;

}
