package com.jungle.springpost.controller;

import com.jungle.springpost.dto.LoginResponseDto;
import com.jungle.springpost.dto.PostRequestDto;
import com.jungle.springpost.dto.PostSimpleDto;
import com.jungle.springpost.dto.PostSummary;
import com.jungle.springpost.entity.Post;
import com.jungle.springpost.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

	@PostMapping("/api/posts")
	public Post createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
		return postService.createPost(requestDto, request);
	}

	@GetMapping("/api/posts")
	public List<PostSummary> getPost() {
		return postService.getPosts();
	}

	@GetMapping("/api/posts/{id}")
	public PostSimpleDto getPostById(@PathVariable Long id) {

		return postService.getPostsById(id);
	}

	@PutMapping("/api/posts/{id}")
	public Post updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
		return postService.update(id, requestDto, request);
	}

	//postService.update(id, requestDto, request)
	@DeleteMapping("/api/posts/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id, HttpServletRequest request) {
		postService.deletePost(id, request);
		LoginResponseDto loginResponseDto = LoginResponseDto.builder()
			.message("게시글 삭제완료!")
			.statusCode(200)
			.build();
		return ResponseEntity.ok().body(loginResponseDto);
	}

}

