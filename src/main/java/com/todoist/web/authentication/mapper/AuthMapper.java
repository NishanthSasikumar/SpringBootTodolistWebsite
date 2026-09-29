package com.todoist.web.authentication.mapper;

import com.todoist.web.authentication.DTO.SignupRequest;
import com.todoist.web.authentication.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toEntity(SignupRequest signupRequest);
}
