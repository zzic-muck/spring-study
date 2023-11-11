package com.jungle.springpost.repository;

import com.jungle.springpost.dto.PostSummary;
import com.jungle.springpost.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<PostSummary> findAllByOrderByModifiedAtDesc();

}
