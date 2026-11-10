package com.todoist.web.resetpassword.repository;

import com.todoist.web.resetpassword.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgetPasswordRepository extends JpaRepository<PasswordReset,Long> {

}
