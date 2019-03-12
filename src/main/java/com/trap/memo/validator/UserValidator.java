package com.trap.memo.validator;

import com.trap.memo.model.User;
import com.trap.memo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


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

        if (user.getUsername().isEmpty()){
            errors.rejectValue("username", "Required");
        } else
        if (user.getPassword().isEmpty()){
            errors.rejectValue("password", "Required");
        } else
        if (user.getConfirmPassword().isEmpty()){
            errors.rejectValue("confirmPassword", "Required");
        } else
        if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        } else
        if (userService.isSuchUserExist(user.getUsername())) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        } else
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        } else
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
