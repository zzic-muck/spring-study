package com.jungle.springpost.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}
