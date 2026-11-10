package com.todoist.web.resetpassword.DTOs.updatepassword;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatepasswordRequest{
    String token;
    String password;
    String confirmPassword;
}
