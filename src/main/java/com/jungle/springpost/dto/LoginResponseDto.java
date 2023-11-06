package com.jungle.springpost.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class LoginResponseDto {

    private String message;
    private int statusCode;
}
