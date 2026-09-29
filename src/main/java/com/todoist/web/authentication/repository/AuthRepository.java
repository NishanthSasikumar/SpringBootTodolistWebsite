package com.todoist.web.authentication.repository;

import com.todoist.web.authentication.entity.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends ListCrudRepository<User,Long>{
    boolean existsByEmail(String email);
    User findByEmail(String email);
}