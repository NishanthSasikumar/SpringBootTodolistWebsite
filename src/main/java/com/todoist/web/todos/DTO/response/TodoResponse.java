package com.todoist.web.todos.DTO.response;

import com.todoist.web.todos.enums.Priority;
import com.todoist.web.todos.enums.Status;

import java.time.LocalDate;

public class TodosResponse {
    Long id;
    String title;
    String description;
    Priority priority;
    LocalDate duedate;
    Status status;
}
