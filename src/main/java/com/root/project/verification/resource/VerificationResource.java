package com.root.project.verification.resource;

import com.root.project.user.User;
import com.root.project.user.repository.UserRepository;
import com.root.project.user.service.UserService;
import com.root.project.verification.Verification;
import com.root.project.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class VerificationResource {
    private final VerificationService verificationService;
    private final UserService userService;

    @GetMapping("/verify/email")
    public String verifyEmail(@RequestParam String id, RedirectAttributes redirectAttributes){
        String userForVerification = verificationService.findUserForVerification(id);
        if(userForVerification == null){
            throw new UsernameNotFoundException("invalid verification");
        }
        User user = this.userService.verfiedUser(userForVerification);
        redirectAttributes.addAttribute("username", user.getUsername());
        return "redirect:/verified";
    }

    @GetMapping("verified")
    public String verificationPage(){
        return "verified";
    }

}
