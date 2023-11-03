package com.jungle.springpost.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class PostSimpleDto {
    private String contents;
    private String title;
    private String writer;
    private LocalDateTime createdAt;
}
