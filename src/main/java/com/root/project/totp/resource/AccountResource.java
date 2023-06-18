package com.root.project.totp.resource;

import com.root.project.totp.dto.TOTPCode;
import com.root.project.exception.InvalidTOTPVerificationCode;
import com.root.project.totp.service.TOTPService;
import com.root.project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AccountResource {
    private final TOTPService totpService;

    @GetMapping("/totp-login")
    public  String totpLogin(){
        return "totp-login";
    }
    @GetMapping("/totp-login-error")
    public String totpLoginError(Model model){
        model.addAttribute("loginError",true);
        return "totp-login";
    }
    @GetMapping("/account")
    public String  getAccount(Model model, Principal principal){
        boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());
        model.addAttribute("totpEnabled",userHasTotpEnabled);
        return "account";
    }
    @GetMapping("/authenticator-url")
    public String getGoogleAuthenticatorQRUrl(Model model,Principal principal){
        boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());
        if(!userHasTotpEnabled){
            model.addAttribute("qrUrl",totpService.generateNewGoogleAuthQrUrl(principal.getName()));
        }
        model.addAttribute("totpEnabled",userHasTotpEnabled);
        return "account";
    }

    @PostMapping("/confirm-totp")
    public String confirmGoogleAuthenticatorSetup(Model model, Principal principal, @ModelAttribute("codeDto") TOTPCode codeDto){
        boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());
        if(!userHasTotpEnabled){
            User user = totpService.enableTOTPForUser(principal.getName(),Integer.valueOf(codeDto.getCode()));
            model.addAttribute("totpEnabled",user.isTotpEnabled());
        }
        return "account";
    }

//    @ExceptionHandler(InvalidTOTPVerificationCode.class)
//    public String handleInvalidTOTPVerificationCode(Model model,Principal principal){
//
//    }
}
