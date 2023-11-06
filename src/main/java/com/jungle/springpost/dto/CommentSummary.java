package com.jungle.springpost.dto;

import java.time.LocalDateTime;

public interface CommentSummary {
    String getUsername();
    String getComment();
    LocalDateTime getCreatedAt();
}
