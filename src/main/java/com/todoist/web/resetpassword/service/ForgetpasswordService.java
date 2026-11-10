package com.todoist.web.resetpassword.service;

import com.todoist.web.resetpassword.record.ForgetPasswordEvent;
import com.todoist.web.resetpassword.repository.ForgetPasswordRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@AllArgsConstructor
public class ForgetpasswordService {

    JavaMailSender javaMailSender;

    @Value("${reset.passwordurl}")
    String resetPasswordUrl;

    @Value("${reset.senderMail}")
    String senderMail;


    @Async
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void sendEmail(ForgetPasswordEvent forgetPasswordEvent){

        String url=resetPasswordUrl+"?token="+forgetPasswordEvent.token();

        SimpleMailMessage message=new SimpleMailMessage();

        message.setFrom(senderMail);
        message.setTo(forgetPasswordEvent.email());
        message.setSubject("TodoList - Password Reset");

        message.setText(
                "Hello,\n\n" +
                        "We received a request to reset your password.\n\n" +
                        "Click the following link to reset your password:\n" +
                        url +
                        "\n\nThis link expires in 15 minutes.\n\n" +
                        "If you did not request this, you can ignore this email."
        );

        javaMailSender.send(message);
    }


}
