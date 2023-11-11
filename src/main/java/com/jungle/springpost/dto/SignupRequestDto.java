package com.jungle.springpost.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "username은  최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다")
    private String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,15}$", message = "password는  최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 합니다")
    private String password;

    private String email;
    private boolean admin=false;
    private String adminToken;
}
