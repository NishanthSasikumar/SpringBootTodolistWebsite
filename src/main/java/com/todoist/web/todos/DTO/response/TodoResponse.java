package com.todoist.web.todos.DTO.response;

import com.todoist.web.todos.enums.Priority;
import com.todoist.web.todos.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponse {
    Long id;
    String title;
    String description;
    Priority priority;
    LocalDate duedate;
    Status status;
}
