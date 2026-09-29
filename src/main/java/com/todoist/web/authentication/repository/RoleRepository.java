package com.todoist.web.authentication.repository;

import com.todoist.web.authentication.entity.Role;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RoleRepository extends ListCrudRepository<Role,Long> {

    Optional<Role> findByRole(String role);
}
