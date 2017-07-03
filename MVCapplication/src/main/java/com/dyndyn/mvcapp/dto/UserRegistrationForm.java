package com.dyndyn.mvcapp.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Size;

/**
 * A simple POJO that holds fields required for user registration
 */
public class UserRegistrationForm extends UserLoginForm {

    @NotBlank(message = "{error.name.notnull}")
    @Size(min = 4, max = 64, message = "{error.name.size}")
    private String name;

    @NotBlank(message = "{error.password.confirmation.notnull}")
    @Size(min = 4, max = 64, message = "{error.password.size}")
    private String confirmPassword;

    @NotBlank(message = "{error.address.notnull}")
    @Size(min = 4, max = 255, message = "{error.address.size}")
    private String address;

    public UserRegistrationForm() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = HtmlUtils.htmlEscape(name);
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = HtmlUtils.htmlEscape(confirmPassword);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
