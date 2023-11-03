package com.jungle.springpost.dto;

import java.time.LocalDateTime;

public interface PostSummary {
    String getTitle();
    String getWriter();
    String getContents();
    LocalDateTime getCreatedAt();
}
