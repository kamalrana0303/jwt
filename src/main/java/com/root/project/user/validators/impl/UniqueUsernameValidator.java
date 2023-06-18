package com.root.project.user.validators.impl;

import com.root.project.user.repository.UserRepository;
import com.root.project.user.validators.UniqueUsername;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {
    private UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return username!=null && userRepository.findUserByUsername(username).isEmpty();
    }
}
