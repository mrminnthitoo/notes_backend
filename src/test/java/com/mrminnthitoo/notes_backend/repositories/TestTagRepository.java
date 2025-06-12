package com.mrminnthitoo.notes_backend.repositories;

import com.mrminnthitoo.notes_backend.models.entities.Tag;
import com.mrminnthitoo.notes_backend.models.entities.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTagRepository {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    public void testCreateTag(){
        Optional<User> userResult = this.userRepository.findById(1L);
        if (userResult.isPresent()){

            User user = userResult.get();

            Tag tag = new Tag();
            tag.setName("IT");

            tag.setUser(user);
            user.getTags().add(tag);

            Tag createdTag = this.tagRepository.save(tag);
            assertEquals("IT", createdTag.getName());

        }
    }

}
