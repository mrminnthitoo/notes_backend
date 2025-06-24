package com.mrminnthitoo.notes_backend.services.implementations;

import com.mrminnthitoo.notes_backend.exceptions.NotFoundException;
import com.mrminnthitoo.notes_backend.helpers.Mapper;
import com.mrminnthitoo.notes_backend.models.dtos.TagDto;
import com.mrminnthitoo.notes_backend.models.dtos.TagNotesDto;
import com.mrminnthitoo.notes_backend.models.entities.Tag;
import com.mrminnthitoo.notes_backend.models.entities.User;
import com.mrminnthitoo.notes_backend.repositories.TagRepository;
import com.mrminnthitoo.notes_backend.repositories.UserRepository;
import com.mrminnthitoo.notes_backend.services.TagService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImplementation implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;


    @Override
    public List<TagDto> findAllTags(Long userId) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();
            List<Tag> tags = this.tagRepository.findByUser(user, Sort.by(this.getSortOrder()));
            return this.mapper.mapList(tags, TagDto.class);

        }else {
            throw new NotFoundException(
                    "user not found.",
                    "userId",
                    "userId " + userId + " not found."
            );
        }
    }

    @Transactional
    @Override
    public TagNotesDto findTagById(Long userId, Long tagId) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();
            Optional<Tag> tagResult = this.tagRepository.findByIdAndUser(tagId, user);

            if (tagResult.isPresent()){
                Tag tag = tagResult.get();
                return this.mapper.map(tag, TagNotesDto.class);
            }else {

                throw new NotFoundException(
                        "tag not found.",
                        "tagId",
                        "tagId " + tagId + " not found."
                );

            }

        }else {
            throw new NotFoundException(
                    "user not found.",
                    "userId",
                    "userId " + userId + " not found."
            );
        }
    }

    @Transactional
    @Override
    public TagDto createTag(Long userId, TagDto tagDto) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Tag tag = new Tag();
            tag.setName(tagDto.getName());

            tag.setUser(user);
            user.getTags().add(tag);

            Tag createdTag = this.tagRepository.save(tag);
            return this.mapper.map(createdTag, TagDto.class);

        }else {
            throw new NotFoundException(
                    "user not found.",
                    "userId",
                    "userId " + userId + " not found."
            );
        }
    }

    @Transactional
    @Override
    public TagDto updateTag(Long userId, TagDto tagDto) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Optional<Tag> tagResult = this.tagRepository.findByIdAndUser(tagDto.getId(), user);

            if (tagResult.isPresent()){

                Tag tag = tagResult.get();

                tag.setName(tagDto.getName());

                Tag updatedTag = this.tagRepository.save(tag);
                return this.mapper.map(updatedTag, TagDto.class);

            }else {

                throw new NotFoundException(
                        "tag not found.",
                        "tagId",
                        "tagId " + tagDto.getId() + " not found."
                );

            }

        }else {
            throw new NotFoundException(
                    "user not found.",
                    "userId",
                    "userId " + userId + " not found."
            );
        }
    }

    @Transactional
    @Override
    public TagDto deleteTag(Long userId, Long tagId) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Optional<Tag> tagResult = this.tagRepository.findByIdAndUser(tagId, user);

            if (tagResult.isPresent()){

                Tag tag = tagResult.get();

                this.tagRepository.delete(tag);
                return this.mapper.map(tag, TagDto.class);

            }else {

                throw new NotFoundException(
                        "tag not found.",
                        "tagId",
                        "tagId " + tagId + " not found."
                );

            }

        }else {
            throw new NotFoundException(
                    "user not found.",
                    "userId",
                    "userId " + userId + " not found."
            );
        }
    }

    private Optional<User> findUser(Long userId){
        return this.userRepository.findById(userId);
    }

    private Sort.Order getSortOrder(){
        return Sort.Order.asc("name");
    }
}
