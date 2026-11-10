package com.todoist.web.todos.repository;

import com.todoist.web.todos.DTO.response.TodoResponse;
import com.todoist.web.todos.entity.Todos;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends ListCrudRepository<Todos,Long> {
      List<Todos> findAllByUser_Id(Long id);
      Todos findByIdAndUser_id(Long id,Long user_id);
}
