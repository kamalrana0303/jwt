package com.root.project.totp.service;

import com.root.project.role.service.RoleService;
import com.root.project.totp.TOTPDetails;
import com.root.project.exception.InvalidTOTPVerificationCode;
import com.root.project.totp.repository.TOTPRepository;
import com.root.project.user.User;
import com.root.project.user.repository.UserRepository;
import com.root.project.user.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TOTPServiceImpl implements TOTPService{
    private final UserRepository userRepository;
    private final TOTPRepository totpRepository;
    private final String ISSUER="ONELAP";
    private final GoogleAuthenticator googleAuth;



    @Override
    public boolean isTotpEnabled(String username){
        Optional<User> userByUsernameOrEmail = userRepository.findUserByUsernameOrEmail(username);
        User us = userByUsernameOrEmail.map(user -> {
            user.isTotpEnabled();
            return user;
        }).orElseThrow(() -> new UsernameNotFoundException("invalid user"));

        this.userRepository.save(us);
        return us.isTotpEnabled();
    }

    @Override
    public String generateNewGoogleAuthQrUrl(String username){
        GoogleAuthenticatorKey authKey = googleAuth.createCredentials();
        String secret = authKey.getKey();
        totpRepository.deleteByUsername(username);
        totpRepository.save(new TOTPDetails(username,secret));
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(ISSUER,username,authKey);
    }

    @Override
    public User enableTOTPForUser(String username, Integer code) {
        if(!verifyCode(username,code)){
            throw new InvalidTOTPVerificationCode("verfication code is nto valid");
        }
        User enableTOTPUser = userRepository.findUserByUsernameOrEmail(username).map(u -> {
            u.setTotpEnabled(true);
            return u;
        }).orElseThrow(() -> new UsernameNotFoundException("invalid username"));
        User user = this.userRepository.save(enableTOTPUser);
        return user;
    }

    @Override
    public boolean verifyCode(String username, int code){
        TOTPDetails totpDetails = totpRepository.findByUsername(username);
        return googleAuth.authorize(totpDetails.getSecret(),code);
    }
}
