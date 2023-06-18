package com.root.project.user.validators.impl;

import com.root.project.user.dto.UserDto;
import com.root.project.user.validators.PasswordConfirmed;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, Object> {
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        String password = ((UserDto)  obj).getPassword();
        String confirmPassword = ((UserDto)  obj).getConfirmPassword();
        return password.equals(confirmPassword);
    }
}
