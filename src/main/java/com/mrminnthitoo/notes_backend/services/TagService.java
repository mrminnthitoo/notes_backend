package com.mrminnthitoo.notes_backend.services;

import com.mrminnthitoo.notes_backend.exceptions.NotFoundException;
import com.mrminnthitoo.notes_backend.models.dtos.TagDto;
import com.mrminnthitoo.notes_backend.models.dtos.TagNotesDto;

import java.util.List;

public interface TagService {

    List<TagDto> findAllTags(Long userId) throws NotFoundException;
    TagNotesDto findTagById(Long userId, Long tagId) throws NotFoundException;
    TagDto createTag(Long userId, TagDto tagDto) throws NotFoundException;
    TagDto updateTag(Long userId, TagDto tagDto) throws NotFoundException;
    TagDto deleteTag(Long userId, Long tagId) throws NotFoundException;

}
