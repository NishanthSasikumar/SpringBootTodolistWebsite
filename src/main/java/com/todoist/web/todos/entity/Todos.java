package com.todoist.web.todos.entity;

import com.todoist.web.authentication.entity.User;
import com.todoist.web.todos.enums.Priority;
import com.todoist.web.todos.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Priority priority;

    private LocalDate duedate;

    private Status status;

    private LocalDateTime completed;

    private LocalDateTime createdAt;

    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
