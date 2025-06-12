package com.mrminnthitoo.notes_backend.services;

import com.mrminnthitoo.notes_backend.exceptions.NotFoundException;
import com.mrminnthitoo.notes_backend.models.dtos.NoteCreationDto;
import com.mrminnthitoo.notes_backend.models.dtos.NoteDto;

import java.util.List;

public interface NoteService {

    List<NoteDto> findAllNotes(Long userId) throws NotFoundException;
    NoteDto getNoteById(Long userId, Long noteId) throws NotFoundException;
    NoteDto createNote(Long userId, NoteCreationDto noteCreationDto) throws NotFoundException;
    NoteDto updateNote(Long userId, NoteDto noteDto) throws NotFoundException;
    NoteDto deleteNoteById(Long userId, Long noteId) throws NotFoundException;
    NoteDto addNoteToTag(Long userId, Long noteId, Long tagId) throws NotFoundException;
    NoteDto removeNoteFromTag(Long userId, Long noteId, Long tagId) throws NotFoundException;

}
