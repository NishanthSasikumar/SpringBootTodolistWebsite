package com.todoist.web.authentication.entity;

import ch.qos.logback.core.status.InfoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    private Boolean blocked;

    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    public void beforeCreate() {
        createdAt=Instant.now();
        updatedAt=Instant.now();
    }

    @PreUpdate
    public void beforeUpdate(){
        updatedAt=Instant.now();
    }
}
