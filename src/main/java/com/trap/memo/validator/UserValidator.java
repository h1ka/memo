package com.trap.memo.validator;

import com.trap.memo.model.User;
import com.trap.memo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Arrays;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        System.out.println("validate");
        System.out.println(user);
        System.out.println(Arrays.toString(errors.getAllErrors().toArray()));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        System.out.println("after ValidationUtils");
        if (user.getUsername().length() < 8 || user.getUsername().length() > 32) {
            System.out.println("before 1");

            errors.rejectValue("username", "Size.userForm.username");
            System.out.println("1");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            System.out.println("before 2");

            errors.rejectValue("username", "Duplicate.userForm.username");
            System.out.println("2");

        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            System.out.println("before 3");

            errors.rejectValue("password", "Size.userForm.password");

            System.out.println("3");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            System.out.println("before 4");

            errors.rejectValue("confirmPassword", "Different.userForm.password");

            System.out.println("4");
        }
        System.out.println("END");
    }
}
