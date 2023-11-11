package com.jungle.springpost.service;

import com.jungle.springpost.dto.PostCommentDto;
import com.jungle.springpost.dto.PostRequestDto;
import com.jungle.springpost.entity.Post;
import com.jungle.springpost.entity.PostComment;
import com.jungle.springpost.jwt.JwtUtil;
import com.jungle.springpost.repository.CommentRepository;
import com.jungle.springpost.repository.MemberRepository;
import com.jungle.springpost.repository.PostRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final JwtUtil jwtUtil;

	@Transactional
	public PostComment createComment(PostCommentDto commentDto, Long id, HttpServletRequest request) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("댓글을 달 수 없습니다."));

		String token = jwtUtil.resolveToken(request);
		String name = jwtUtil.getUserInfoFromToken(token).getSubject();
		memberRepository.findByUsername(name)
			.orElseThrow(() -> new IllegalArgumentException("비정상적인 사용자입니다."));

		if (jwtUtil.validateToken(token)) {
			commentDto.setUsername(name);
			PostComment comment = new PostComment(commentDto, post);
			commentRepository.save(comment);
			return comment;
		}

		return null;
	}

	@Transactional
	public Long deleteComment(Long id, HttpServletRequest request) {
		Long err = 0L;
		PostComment comment = commentRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("없는 댓글 입니다."));

		String token = jwtUtil.resolveToken(request);
		System.out.println(jwtUtil.getUserInfoFromToken(token).get("auth"));

		if (jwtUtil.validateToken(token)) { //토큰 유효성 검사
			if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("USER")) {
				if (comment.getUsername().equals(jwtUtil.getUserInfoFromToken(token).getSubject())) { //해당 사용자 인지
					commentRepository.deleteById(id);
					return id;
				} else {

					throw new IllegalArgumentException("권한이 없습니다.");
				}
			} else if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("ADMIN")) {
				commentRepository.deleteById(id);
				return id;
			}
		}
		return err;
	}

	@Transactional
	public PostComment Commentupdate(Long id, PostCommentDto requestDto, HttpServletRequest request) {
		PostComment comment = commentRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("없는 댓글 입니다."));

		String token = jwtUtil.resolveToken(request);

		if (jwtUtil.validateToken(token)) { //토큰 유효성 검사
			if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("USER")) {
				if (comment.getUsername().equals(jwtUtil.getUserInfoFromToken(token).getSubject())) { //해당 사용자 인지
					comment.Commentupdate(requestDto);
					return comment;
					//                    return comment.getId();
				} else {
					throw new IllegalArgumentException("권한이 없습니다.");
				}
			} else if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("ADMIN")) {
				comment.Commentupdate(requestDto);
				return comment;
				//                return comment.getId();
			}
		}
		throw new IllegalArgumentException();
	}

}
