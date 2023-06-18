package com.root.project.verification.service;

import com.root.project.user.User;
import com.root.project.verification.Verification;

public interface VerificationService {
    String createVerfication(String username);

    String findUserForVerification(String username);


}
