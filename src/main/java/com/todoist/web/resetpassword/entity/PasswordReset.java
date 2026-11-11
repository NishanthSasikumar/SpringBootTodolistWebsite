package com.todoist.web.resetpassword.entity;

import com.todoist.web.authentication.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passwordreset")
public class PasswordReset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @Column(name="hashedtoken")
    private String hashedToken;

    @Column(name="expiresat")
    private Instant expiresAt;

    private Boolean used;
    public PasswordReset(User user_id, String hashedToken, Instant expiresAt) {
        this.user_id = user_id;
        this.hashedToken = hashedToken;
        this.expiresAt = expiresAt;
        this.used = false;
    }
}
