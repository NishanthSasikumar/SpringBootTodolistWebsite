package com.todoist.web.todos.mapper;

import com.todoist.web.todos.DTO.request.CreateTodoRequest;
import com.todoist.web.todos.DTO.response.TodoResponse;
import com.todoist.web.todos.entity.Todos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todos toTodos(CreateTodoRequest createTodoRequest);
    TodoResponse toResponse(Todos todosResponse);
}
