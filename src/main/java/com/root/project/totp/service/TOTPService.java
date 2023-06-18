package com.root.project.totp.service;

import com.root.project.user.User;

public interface TOTPService {
    boolean isTotpEnabled(String username);

    String generateNewGoogleAuthQrUrl(String username);

    User enableTOTPForUser(String name, Integer valueOf);

    boolean verifyCode(String username, int code);
}
