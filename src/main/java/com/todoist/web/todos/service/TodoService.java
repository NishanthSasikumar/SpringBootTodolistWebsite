package com.todoist.web.todos.service;

import com.todoist.web.authentication.entity.User;
import com.todoist.web.authentication.repository.AuthRepository;
import com.todoist.web.todos.DTO.request.CreateTodoRequest;
import com.todoist.web.todos.DTO.response.TodoResponse;
import com.todoist.web.todos.entity.Todos;
import com.todoist.web.todos.enums.Priority;
import com.todoist.web.todos.enums.Status;
import com.todoist.web.todos.mapper.TodoMapper;
import com.todoist.web.todos.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tools.jackson.databind.JsonNode;


import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    TodoRepository todoRepository;
    AuthRepository authRepository;
    TodoMapper todoMapper;
    public TodoService(TodoRepository todoRepository,TodoMapper todoMapper,AuthRepository authRepository) {
        this.todoRepository=todoRepository;
        this.todoMapper=todoMapper;
        this.authRepository=authRepository;
    }
    public TodoResponse createTodos(CreateTodoRequest createTodoRequest) {
        Todos createTodo = todoMapper.toTodos(createTodoRequest);
        createTodo.setCreatedAt(Instant.now());
        createTodo.setUpdatedAt(Instant.now());
        createTodo.setStatus(Status.PENDING);
        createTodo.setUser(loggedInUser());

        Todos savedTodo = todoRepository.save(createTodo);

        return todoMapper.toResponse(savedTodo);

    }

    public List<TodoResponse> getTodos(){

        User user = authRepository.findByEmail(loggedInUser().getEmail());

        List<Todos> collectionsOfTodos = todoRepository.findAllByUser_Id(user.getId());

        List<TodoResponse> collectionsOfTodoReseponse =new ArrayList<>();

        for(Todos todos: collectionsOfTodos ){
            TodoResponse tempTodoVar = todoMapper.toResponse(todos);
            collectionsOfTodoReseponse.add(tempTodoVar);
        }

        return collectionsOfTodoReseponse;
    }

    @Transactional
    public TodoResponse patchTodo(long id, JsonNode request){

        User user = authRepository.findByEmail(loggedInUser().getEmail());
        Todos todos = todoRepository.findByIdAndUser_id(id,user.getId());

        if(request.has("title")) {
            if(request.get("title").isNull())
                throw new IllegalArgumentException("Invalid values");
            else
                todos.setTitle(request.get("title").asString());
        } else if (request.has("duedate")) {
            if(request.get("duedate").isNull())
                throw new IllegalArgumentException("Invalid values");
            else
                todos.setDuedate(LocalDate.parse(request.get("duedate").asString()));
        } else if(request.has("priority")) {
            if(request.get("priority").isNull())
                throw new IllegalArgumentException("Invalid values");
            else
                todos.setPriority(Priority.valueOf(request.get("priority").asString()));
        } else if(request.has("description")) {
            JsonNode description = request.get("description");
            if(description.isNull())
                todos.setDescription(null);
            else if (description.isString())
                todos.setDescription(description.asString());
            else
                throw new IllegalArgumentException("Invalid values");
        }

        return todoMapper.toResponse(todos);
    }

    @Transactional
    public String deleteTodo(long id){
        User user = authRepository.findByEmail(loggedInUser().getEmail());
        Todos todos = todoRepository.findByIdAndUser_id(id,user.getId());
        todos.set_deleted(true);
        return "Deleted successfully";
    }
    public User loggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return authRepository.findByEmail(email);

    }
}
