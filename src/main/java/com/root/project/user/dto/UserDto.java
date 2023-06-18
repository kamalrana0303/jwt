package com.root.project.user.dto;

import com.root.project.user.validators.PasswordConfirmed;
import com.root.project.user.validators.PasswordPolicy;
import com.root.project.user.validators.UniqueEmail;
import com.root.project.user.validators.UniqueUsername;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@PasswordConfirmed
public class UserDto implements UserDetails {

    @NotEmpty(message = "please enter your password")
    @PasswordPolicy
    private String password;
    @NotEmpty(message = "please confirm your password")
    private String confirmPassword;
    @NotEmpty(message = "please enter your username")
    @UniqueUsername
    private String username;
    @NotEmpty(message = "please enter your firstname")
    private String firstname;
    @NotEmpty(message = "please enter lastname")
    private String lastname;
    @Email(message = "please enter email")
    @UniqueEmail
    private String email;
    private String securityPin;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<SimpleGrantedAuthority> authorities;

}
