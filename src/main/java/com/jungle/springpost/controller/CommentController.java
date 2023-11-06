package com.jungle.springpost.controller;

import com.jungle.springpost.dto.LoginResponseDto;
import com.jungle.springpost.dto.PostCommentDto;
import com.jungle.springpost.dto.PostRequestDto;
import com.jungle.springpost.entity.Post;
import com.jungle.springpost.entity.PostComment;
import com.jungle.springpost.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/api/comment/{id}")
    public PostComment createComment(@RequestBody PostCommentDto commentDto, @PathVariable Long id, HttpServletRequest request) {
        return commentService.createComment(commentDto, id, request);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        commentService.deleteComment(id, request);
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .message("댓글 삭제완료!")
                .statusCode(200)
                .build();
        return ResponseEntity.ok().body(loginResponseDto);
    }

    @PutMapping("/api/comment/{id}")
    public PostComment updateComment(@PathVariable Long id, @RequestBody PostCommentDto requestDto, HttpServletRequest request) {
        return commentService.Commentupdate(id, requestDto, request);
    }

}
