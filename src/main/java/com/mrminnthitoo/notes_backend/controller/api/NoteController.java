package com.mrminnthitoo.notes_backend.controller.api;

import com.mrminnthitoo.notes_backend.API.APIResponse;
import com.mrminnthitoo.notes_backend.API.RESTResponser;
import com.mrminnthitoo.notes_backend.exceptions.NotFoundException;
import com.mrminnthitoo.notes_backend.exceptions.ValidationException;
import com.mrminnthitoo.notes_backend.helpers.AuthUser;
import com.mrminnthitoo.notes_backend.models.dtos.NoteCreationDto;
import com.mrminnthitoo.notes_backend.models.dtos.NoteDto;
import com.mrminnthitoo.notes_backend.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private APIResponse apiResponse;
    @Autowired
    private AuthUser authUser;

    @GetMapping
    public ResponseEntity<RESTResponser> getAllNote() throws NotFoundException {
        List<NoteDto> notes = this.noteService.findAllNotes(this.authUser.getId());
        return this.apiResponse.successResponse(
                HttpStatus.OK,
                "fetch all notes",
                notes
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RESTResponser> getNoteById(@PathVariable(name = "id") Long noteId) throws NotFoundException{
        NoteDto note = this.noteService.getNoteById(this.authUser.getId(), noteId);
        return this.apiResponse.successResponse(
                HttpStatus.OK,
                "fetch specific note.",
                note
        );
    }

    @PostMapping
    public ResponseEntity<RESTResponser> saveNote(@Valid @RequestBody NoteCreationDto noteCreationDto, BindingResult bindingResult) throws NotFoundException, ValidationException {

        if (!bindingResult.hasErrors()){

            NoteDto note = this.noteService.createNote(this.authUser.getId(), noteCreationDto);
            return this.apiResponse.successResponse(
                    HttpStatus.CREATED,
                    "saved note successfully.",
                    note
            );

        }else{
            throw new ValidationException(
                    "validation error.",
                    bindingResult.getFieldErrors()
            );
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<RESTResponser> updateNote(@PathVariable(name = "id") Long noteId, @Valid @RequestBody NoteDto noteDto, BindingResult bindingResult) throws ValidationException, NotFoundException {

        if (!bindingResult.hasErrors()){

            noteDto.setId(noteId);

            NoteDto note = this.noteService.updateNote(this.authUser.getId(), noteDto);
            return this.apiResponse.successResponse(
                    HttpStatus.OK,
                    "updated note successfully.s",
                    note
            );

        }else {
            throw new ValidationException(
                    "validation fail.",
                    bindingResult.getFieldErrors()
            );
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RESTResponser> deleteNote(@PathVariable(name = "id") Long noteId) throws NotFoundException {

        NoteDto note = this.noteService.deleteNoteById(this.authUser.getId(), noteId);

        return this.apiResponse.successResponse(
                HttpStatus.OK,
                "deleted note successfully",
                note
        );

    }

    @PostMapping("/{noteId}/tag/{tagId}")
    public ResponseEntity<RESTResponser> addNoteToTag(@PathVariable Long noteId, @PathVariable Long tagId) throws NotFoundException {

        NoteDto note = this.noteService.addNoteToTag(this.authUser.getId(), noteId, tagId);
        return this.apiResponse.successResponse(
                HttpStatus.CREATED,
                "note added to tag successfully.",
                note
        );

    }

    @DeleteMapping("/{noteId}/tag/{tagId}")
    public ResponseEntity<RESTResponser> removeNoteToTag(@PathVariable Long noteId, @PathVariable Long tagId) throws NotFoundException {

        NoteDto note = this.noteService.removeNoteFromTag(this.authUser.getId(), noteId, tagId);
        return this.apiResponse.successResponse(
                HttpStatus.OK,
                "note removed from tag successfully.",
                note
        );

    }

}
