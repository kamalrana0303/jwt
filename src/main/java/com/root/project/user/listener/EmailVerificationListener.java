package com.root.project.user.listener;

import com.root.project.config.UserAppConfig;
import com.root.project.user.events.UserRegisterationEvent;
import com.root.project.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationListener  implements ApplicationListener<UserRegisterationEvent> {
    private final JavaMailSender mailSender;
    private final VerificationService verificationService;
    private final UserAppConfig userAppConfig;

    @Override
    public void onApplicationEvent(UserRegisterationEvent event) {
        String username = event.getSecurityUser().getUsername();
        String email = event.getSecurityUser().getEmail();
        String verificationId = verificationService.createVerfication(username);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("User account Verification");
        message.setText("Account activation link: "+ userAppConfig.getUrl()+"/verify/email?id="+verificationId);
        message.setTo(email);
        mailSender.send(message);
    }
}
