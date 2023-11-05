package com.jungle.springpost.dto;

import com.jungle.springpost.entity.PostComment;

import java.time.LocalDateTime;
import java.util.List;

public interface PostSummary {
    String getTitle();
    String getWriter();
    String getContents();
    LocalDateTime getCreatedAt();
    List<PostComment> getcommentList();
}
