package com.example.stareshop.services;

import com.example.stareshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class UserValidatorService implements Validator {
    @Override
    public boolean supports(Class<?> someClass) {
        return User.class.equals(someClass);
    }

    @Override
    public void validate(Object userEntity, Errors errors) {
        User user = (User) userEntity;

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.isUsernameEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.isEmailEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.isPasswordEmpty");

        String emailRegexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String passwordRegexPattern = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*)[^\s]{8,}$";

        Boolean isValidEmail =  user.getEmail().matches(emailRegexPattern);
        Boolean isValidPassword = user.getPassword().matches(passwordRegexPattern);
        Boolean arePasswordTheSame = user.getPassword().equals(user.getPasswordConfirm());


        if(!isValidEmail)
            errors.rejectValue("email", "user.isValidEmail");
        if(!isValidPassword)
            errors.rejectValue("password", "user.isValidPassword");
        if(!arePasswordTheSame)
            errors.rejectValue("passwordConfirm", "user.isPasswordTheSame");
    }
}
