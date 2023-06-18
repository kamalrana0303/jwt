package com.root.project.verification.service;

import com.root.project.user.User;
import com.root.project.user.service.UserService;
import com.root.project.verification.Verification;
import com.root.project.verification.repository.VerificationRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService{
    private final VerificationRepsitory verificationRepsitory;

    @Override
    public String createVerfication(String username){
        Verification verification = new Verification( UUID.randomUUID().toString(),username,new Date());
        return verificationRepsitory.save(verification).getVerificationId();
    }

    @Override
    public String findUserForVerification(String id) {
        Verification verification = verificationRepsitory.findByVerificationId(id);
        return verification.getUsername();
    }
}
