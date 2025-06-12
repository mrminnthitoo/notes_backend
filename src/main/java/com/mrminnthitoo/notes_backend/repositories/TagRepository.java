package com.mrminnthitoo.notes_backend.repositories;

import com.mrminnthitoo.notes_backend.models.entities.Tag;
import com.mrminnthitoo.notes_backend.models.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByUser(User user, Sort sort);
    Optional<Tag> findByIdAndUser(Long id, User user);

}
