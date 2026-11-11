package com.todoist.web.resetpassword.repository;

import com.todoist.web.resetpassword.entity.PasswordReset;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface ForgetPasswordRepository extends JpaRepository<PasswordReset,Long> {
    Optional<PasswordReset> findByHashedTokenAndUsedFalse(String token);
    @Modifying
    @Query("""
    UPDATE PasswordReset t set t.used=true where t.id=:id and t.used=false and t.expiresAt>:now
    """)
    @Transactional
    int consumeToken(
            @Param("id") Long id,
            @Param("now") Instant now
    );
}
