package com.jungle.springpost.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PostCommentDto {
    private String username;
    private String comment;
}
