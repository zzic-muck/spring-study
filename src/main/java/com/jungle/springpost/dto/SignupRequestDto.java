package com.jungle.springpost.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,15}$")
    private String password;

    private String email;
    private boolean admin=false;
    private String adminToken;
}
