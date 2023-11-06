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
        if (jwtUtil.validateToken(token)) {
            commentDto.setUsername(name);
            PostComment comment = new PostComment(commentDto, post);
            commentRepository.save(comment);
            return comment;
        }


        return null;
    }

    @Transactional
    public Long deleteComment(Long id, HttpServletRequest request){
        Long err = 0L;
        PostComment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("없는 댓글 입니다."));

        String token = jwtUtil.resolveToken(request);
        System.out.println(jwtUtil.getUserInfoFromToken(token).get("auth"));

        if(jwtUtil.validateToken(token)) { //토큰 유효성 검사
            if(jwtUtil.getUserInfoFromToken(token).get("auth").equals("USER")) {
                if (comment.getUsername().equals(jwtUtil.getUserInfoFromToken(token).getSubject())) { //해당 사용자 인지
                    commentRepository.deleteById(id);
                    return id;
                }
                else{

                    return 0L;
                }
            } else if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("ADMIN")) {
                commentRepository.deleteById(id);
                return id;
            }
        }
        return err;
    }

    @Transactional
    public PostComment Commentupdate(Long id, PostCommentDto requestDto, HttpServletRequest request){
        PostComment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는 댓글 입니다."));

        String token = jwtUtil.resolveToken(request);

        if(jwtUtil.validateToken(token)) { //토큰 유효성 검사
            if(jwtUtil.getUserInfoFromToken(token).get("auth").equals("USER")) {
                if (comment.getUsername().equals(jwtUtil.getUserInfoFromToken(token).getSubject())) { //해당 사용자 인지
                    comment.Commentupdate(requestDto);
                    return comment;
//                    return comment.getId();
                }
                else{
                    throw new IllegalArgumentException();
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
