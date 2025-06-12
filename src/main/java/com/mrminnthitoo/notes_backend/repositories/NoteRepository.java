package com.mrminnthitoo.notes_backend.repositories;

import com.mrminnthitoo.notes_backend.models.entities.Note;
import com.mrminnthitoo.notes_backend.models.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser(User user, Sort sort);

    Optional<Note> findByUserAndId(User user, Long id);
}
