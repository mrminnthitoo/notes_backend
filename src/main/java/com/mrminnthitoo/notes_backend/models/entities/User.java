package com.mrminnthitoo.notes_backend.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Base{

    @Column
    private String name;

    @Column(unique = true)
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Date confirmedAt;

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            },
            mappedBy = "user"
    )
    private Set<Note> notes = new HashSet<>();

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            },
            mappedBy = "user"
    )
    private Set<Tag> tags = new HashSet<>();

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();


}
