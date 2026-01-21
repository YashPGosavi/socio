package com.socio.socio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="groups")
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;
}
