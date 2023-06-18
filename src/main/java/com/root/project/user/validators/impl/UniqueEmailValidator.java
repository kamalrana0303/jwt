package com.root.project.user.validators.impl;

import com.root.project.user.repository.UserRepository;
import com.root.project.user.validators.UniqueEmail;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {
    private  UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email!=null && this.userRepository.findUserByEmail(email).isEmpty();
    }
}
