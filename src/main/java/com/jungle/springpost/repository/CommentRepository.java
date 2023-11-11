package com.jungle.springpost.repository;

import com.jungle.springpost.dto.PostCommentDto;
import com.jungle.springpost.entity.PostComment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<PostComment, Long> {
	List<PostComment> findAllByOrderByModifiedAtDesc();
}
