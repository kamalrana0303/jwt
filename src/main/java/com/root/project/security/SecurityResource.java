package com.root.project.security;

import com.root.project.portfolio.service.PortfolioService;
import com.root.project.user.User;
import com.root.project.user.dto.UserDto;
import com.root.project.user.events.UserRegisterationEvent;
import com.root.project.user.model.request.UserRm;
import com.root.project.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("/api")
@RequiredArgsConstructor
public class SecurityResource {
    private final UserServiceImpl userService;
    private final PortfolioService portfolioService;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/login")
    ResponseEntity<?> apiLogin(@RequestBody UserRm userRm){
        return ResponseEntity.ok(null);
    }


    @GetMapping("/register")
    public String getRegister(Model model){
            model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("user") UserDto user, BindingResult result){
        if(result.hasErrors()){
            return "register";
        }
        user.setEnabled(false);
        User save = userService.createUser(user);
        this.portfolioService.createPorfolio(user);
        eventPublisher.publishEvent(new UserRegisterationEvent(new SecurityUser(save)));
        return "redirect:register?success";
    }

}
