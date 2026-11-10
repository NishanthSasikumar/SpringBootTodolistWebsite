package com.todoist.web.resetpassword.record;

public record ForgetPasswordEvent(
        String token,
        String email
) {
}
