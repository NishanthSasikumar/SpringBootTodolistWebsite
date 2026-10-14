package com.todoist.web.todos.DTO.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTodosRequest {

    @NotBlank(message = "Title can't be Blank")
    String title;
    String description;
    @NotNull(message = "Duedate can't be empty")
    @FutureOrPresent(message = "Due date cannot be in the past")
    LocalTime duedate;
    @NotBlank(message = "Priority can't be blank")
    String priority;
}
