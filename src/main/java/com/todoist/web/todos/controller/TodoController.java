package com.todoist.web.todos.controller;

import com.todoist.web.todos.DTO.request.CreateTodoRequest;
import com.todoist.web.todos.DTO.response.TodoResponse;
import com.todoist.web.todos.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    TodoService todoService;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    @PostMapping
    public TodoResponse postTodos(@RequestBody CreateTodoRequest createTodoRequest) {
        return todoService.createTodos(createTodoRequest);
    }

    @GetMapping
    public List<TodoResponse> getTodos() {
        System.out.println("In Controller function");
        return todoService.getTodos();
    }

    @PatchMapping("/{id}")
    public TodoResponse patchTodos(@PathVariable long id, @RequestBody JsonNode request){

        return todoService.patchTodo(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodos(@PathVariable long id){
        return ResponseEntity.ok(todoService.deleteTodo(id));
    }
}
