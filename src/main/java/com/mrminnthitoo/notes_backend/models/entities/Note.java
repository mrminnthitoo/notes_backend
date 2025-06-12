package com.mrminnthitoo.notes_backend.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "notes")
public class Note extends Base{

    @Column
    private String title;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            }
    )
    private User user;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            },
            mappedBy = "notes"
    )
    private Set<Tag> tags = new HashSet<>();

}
