package com.todoist.web.todos.DTO.request;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTodosRequest {

    String title;
    String description;
    @FutureOrPresent(message = "Due date cannot be in the past")
    LocalTime duedate;
    String priority;
}
