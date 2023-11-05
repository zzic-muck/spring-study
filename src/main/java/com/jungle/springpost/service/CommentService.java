package com.jungle.springpost.service;

import com.jungle.springpost.dto.PostCommentDto;
import com.jungle.springpost.dto.PostRequestDto;
import com.jungle.springpost.entity.Post;
import com.jungle.springpost.entity.PostComment;
import com.jungle.springpost.jwt.JwtUtil;
import com.jungle.springpost.repository.CommentRepository;
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
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public PostComment createComment(PostCommentDto commentDto, Long id, HttpServletRequest request){
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        String token = jwtUtil.resolveToken(request);
        String name = jwtUtil.getUserInfoFromToken(token).getSubject();
        commentDto.setUsername(name);

        PostComment comment = new PostComment(commentDto, post);
        commentRepository.save(comment);
        return comment;
    }
}
