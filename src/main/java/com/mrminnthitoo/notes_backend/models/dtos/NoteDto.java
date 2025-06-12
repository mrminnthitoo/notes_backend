package com.mrminnthitoo.notes_backend.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NoteDto {

    private Long id;
    private String title;
    private String content;
    private List<TagDto> tags;

}
