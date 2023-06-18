package com.root.project.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutheticationRequest {
    private String email;
    private String  password;
}
