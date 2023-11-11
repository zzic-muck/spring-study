package com.jungle.springpost.dto;

import com.jungle.springpost.entity.PostComment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PostSimpleDto {
	private String contents;
	private String title;
	private String writer;
	private LocalDateTime createdAt;
	private List<PostComment> postComments;
}
