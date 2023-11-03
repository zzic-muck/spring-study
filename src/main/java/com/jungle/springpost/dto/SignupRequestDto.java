package com.jungle.springpost.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;


@Getter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,15}$")
    private String password;

    private String email;

}
