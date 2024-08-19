package com.shep.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"User\"") //Using this because PostgreSQL has User as an included variable and other way it conflicts wit DB
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_id_gen")
    @SequenceGenerator(name = "User_id_gen", sequenceName = "User_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @PrePersist
    protected void onCreate() {
        creationDate = Timestamp.from(Instant.now());
    }
}