package com.mrminnthitoo.notes_backend.controller.api;

import com.mrminnthitoo.notes_backend.API.APIResponse;
import com.mrminnthitoo.notes_backend.API.RESTResponser;
import com.mrminnthitoo.notes_backend.exceptions.NotFoundException;
import com.mrminnthitoo.notes_backend.exceptions.ValidationException;
import com.mrminnthitoo.notes_backend.helpers.AuthUser;
import com.mrminnthitoo.notes_backend.models.dtos.TagDto;
import com.mrminnthitoo.notes_backend.models.dtos.TagNotesDto;
import com.mrminnthitoo.notes_backend.services.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private AuthUser authUser;
    @Autowired
    private APIResponse apiResponse;

    @GetMapping
    public ResponseEntity<RESTResponser> getAllTags() throws NotFoundException {

        List<TagDto> tags = this.tagService.findAllTags(this.authUser.getId());
        return this.apiResponse.successResponse(
                HttpStatus.OK,
                "fetch all tags",
                tags
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<RESTResponser> getTagById(@PathVariable(name = "id") Long tagId) throws NotFoundException {

        TagNotesDto tagNotes = this.tagService.findTagById(this.authUser.getId(), tagId);
        return this.apiResponse.successResponse(
                HttpStatus.OK,
                "fetch specific tag",
                tagNotes
        );

    }

    @PostMapping
    public ResponseEntity<RESTResponser> createTag(@Valid @RequestBody TagDto tagDto, BindingResult bindingResult) throws ValidationException, NotFoundException {

        if (!bindingResult.hasErrors()){

            TagDto tag = this.tagService.createTag(this.authUser.getId(), tagDto);
            return this.apiResponse.successResponse(
                    HttpStatus.CREATED,
                    "tag saved successfully.",
                    tag
            );

        }else {

            throw new ValidationException(
                    "validation fail.",
                    bindingResult.getFieldErrors()
            );

        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<RESTResponser> updateTag(@PathVariable(name = "id") Long tagId, @Valid @RequestBody TagDto tagDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if (!bindingResult.hasErrors()){

            tagDto.setId(tagId);
            TagDto tag = this.tagService.updateTag(this.authUser.getId(), tagDto);
            return this.apiResponse.successResponse(
                    HttpStatus.OK,
                    "tag updated successfully.",
                    tag
            );

        }else {

            throw new ValidationException(
                    "validation fail.",
                    bindingResult.getFieldErrors()
            );

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RESTResponser> deleteTag(@PathVariable(name = "id") Long tagId) throws NotFoundException {
        TagDto tag = this.tagService.deleteTag(this.authUser.getId(), tagId);
        return this.apiResponse.successResponse(
                HttpStatus.OK,
                "tag deleted successfully.",
                tag
        );
    }

}
