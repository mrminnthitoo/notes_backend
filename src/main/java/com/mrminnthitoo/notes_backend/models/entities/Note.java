package com.mrminnthitoo.notes_backend.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notes")
public class Note extends Base{

    @Column
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            }
    )
    private User user;

}
