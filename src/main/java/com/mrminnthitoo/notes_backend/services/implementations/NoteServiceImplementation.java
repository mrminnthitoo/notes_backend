package com.mrminnthitoo.notes_backend.services.implementations;

import com.mrminnthitoo.notes_backend.exceptions.NotFoundException;
import com.mrminnthitoo.notes_backend.helpers.Mapper;
import com.mrminnthitoo.notes_backend.models.dtos.NoteCreationDto;
import com.mrminnthitoo.notes_backend.models.dtos.NoteDto;
import com.mrminnthitoo.notes_backend.models.entities.Note;
import com.mrminnthitoo.notes_backend.models.entities.Tag;
import com.mrminnthitoo.notes_backend.models.entities.User;
import com.mrminnthitoo.notes_backend.repositories.NoteRepository;
import com.mrminnthitoo.notes_backend.repositories.TagRepository;
import com.mrminnthitoo.notes_backend.repositories.UserRepository;
import com.mrminnthitoo.notes_backend.services.NoteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImplementation implements NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public List<NoteDto> findAllNotes(Long userId) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            return this.mapper.mapList(
                    this.noteRepository.findByUser(
                            userResult.get(),
                            Sort.by(this.getSortOrder()
                        )
                    ),
                    NoteDto.class
            );

        }else {
            throw new NotFoundException(
                    "user not found.",
                    "userId",
                    "userId " + userId + " not found."
            );
        }
    }

    @Override
    public NoteDto getNoteById(Long userId, Long noteId) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Optional<Note> noteResult = this.noteRepository.findByUserAndId(user, noteId);
            if (noteResult.isPresent()){
                return this.mapper.map(noteResult.get(), NoteDto.class);
            }else {
                throw new NotFoundException(
                        "note not found.",
                        "noteId",
                        "noteId " + userId + " not found."
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
    public NoteDto createNote(Long userId, NoteCreationDto noteCreationDto) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Note note = new Note();
            note.setTitle(noteCreationDto.getTitle());
            note.setContent(noteCreationDto.getContent());

            note.setUser(user);
            user.getNotes().add(note);

            Note createdNote = this.noteRepository.save(note);
            return this.mapper.map(createdNote, NoteDto.class);


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
    public NoteDto updateNote(Long userId, NoteDto noteDto) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Optional<Note> noteResult = this.noteRepository.findByUserAndId(user, noteDto.getId());
            if (noteResult.isPresent()){

                Note note = noteResult.get();
                note.setTitle(noteDto.getTitle());
                note.setContent(noteDto.getContent());

                Note updatedNote = this.noteRepository.save(note);
                return this.mapper.map(updatedNote, NoteDto.class);

            }else{

                throw new NotFoundException(
                        "note not found.",
                        "noteId",
                        "noteId " + userId + " not found."
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

    @Override
    public NoteDto deleteNoteById(Long userId, Long noteId) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Optional<Note> noteResult = this.noteRepository.findByUserAndId(user, noteId);
            if (noteResult.isPresent()){

                Note note = noteResult.get();

                // delete note
                this.noteRepository.delete(note);

                return this.mapper.map(note, NoteDto.class);

            }else{

                throw new NotFoundException(
                        "note not found.",
                        "noteId",
                        "noteId " + userId + " not found."
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
    public NoteDto addNoteToTag(Long userId, Long noteId, Long tagId) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Optional<Note> noteResult = this.noteRepository.findByUserAndId(user, noteId);
            if (noteResult.isPresent()){

                Note note = noteResult.get();

                Optional<Tag> tagResult = this.tagRepository.findByIdAndUser(tagId, user);
                if (tagResult.isPresent()){

                    Tag tag = tagResult.get();

                    note.getTags().add(tag);
                    tag.getNotes().add(note);
                    Note addedNote = this.noteRepository.save(note);
                    return this.mapper.map(addedNote, NoteDto.class);

                }else {
                    throw new NotFoundException(
                            "tag not found.",
                            "tagId",
                            "tagId " + tagId + " not found."
                    );
                }

            }else{

                throw new NotFoundException(
                        "note not found.",
                        "noteId",
                        "noteId " + userId + " not found."
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

    @Override
    public NoteDto removeNoteFromTag(Long userId, Long noteId, Long tagId) throws NotFoundException {
        Optional<User> userResult = this.findUser(userId);
        if (userResult.isPresent()){

            User user = userResult.get();

            Optional<Note> noteResult = this.noteRepository.findByUserAndId(user, noteId);
            if (noteResult.isPresent()){

                Note note = noteResult.get();

                Optional<Tag> tagResult = this.tagRepository.findByIdAndUser(tagId, user);
                if (tagResult.isPresent()){

                    Tag tag = tagResult.get();

                    note.getTags().remove(tag);
                    tag.getNotes().remove(note);
                    Note addedNote = this.noteRepository.save(note);
                    return this.mapper.map(addedNote, NoteDto.class);

                }else {
                    throw new NotFoundException(
                            "tag not found.",
                            "tagId",
                            "tagId " + tagId + " not found."
                    );
                }

            }else{

                throw new NotFoundException(
                        "note not found.",
                        "noteId",
                        "noteId " + userId + " not found."
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
        return Sort.Order.desc("updatedAt");
    }
}
