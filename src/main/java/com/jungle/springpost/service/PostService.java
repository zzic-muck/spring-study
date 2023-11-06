package com.jungle.springpost.service;

import com.jungle.springpost.dto.PostRequestDto;
import com.jungle.springpost.dto.PostSimpleDto;
import com.jungle.springpost.dto.PostSummary;
import com.jungle.springpost.entity.Post;
import com.jungle.springpost.jwt.JwtUtil;
import com.jungle.springpost.repository.MemberRepository;
import com.jungle.springpost.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Post createPost(PostRequestDto requestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);

        if(!jwtUtil.validateToken(token)){ //유효한 토큰일 경우에만 게시글 작성가능
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        requestDto.setUsername(jwtUtil.getUserInfoFromToken(token).getSubject());
        Post post = new Post(requestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public List<PostSummary> getPosts() {
        
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public PostSimpleDto getPostsById(Long id) {
        Post p = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        PostSimpleDto postSimpleDto =  PostSimpleDto.builder()
                                                    .title(p.getTitle())
                                                    .contents(p.getContents())
                                                    .writer(p.getWriter())
                                                    .createdAt(p.getCreatedAt())
                                                    .postComments(p.getCommentList()).build();

        return postSimpleDto;
    }

    @Transactional
    public Post update(Long id, PostRequestDto requestDto, HttpServletRequest request){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글 입니다."));

        String token = jwtUtil.resolveToken(request);

        if(jwtUtil.validateToken(token)) { //토큰 유효성 검사
            if(jwtUtil.getUserInfoFromToken(token).get("auth").equals("USER")) {
                if (post.getWriter().equals(jwtUtil.getUserInfoFromToken(token).getSubject())) { //해당 사용자 인지
                    post.update(requestDto);
                    return post;
//                    return post.getId();
                }
                else{
                    throw new IllegalArgumentException("수정 권한이 없습니다.");
                }
            } else if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("ADMIN")) {
                post.update(requestDto);
                return post;
//                return post.getId();
            }
        }
        return null;
//        return 0L;
    }

    @Transactional
    public Long deletePost(Long id, HttpServletRequest request){

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글 입니다."));

        String token = jwtUtil.resolveToken(request);
        System.out.println(jwtUtil.getUserInfoFromToken(token).get("auth"));

        if(jwtUtil.validateToken(token)) { //토큰 유효성 검사
            if(jwtUtil.getUserInfoFromToken(token).get("auth").equals("USER")) {
                if (post.getWriter().equals(jwtUtil.getUserInfoFromToken(token).getSubject())) { //해당 사용자 인지
                    postRepository.deleteById(id);
                    return id;
                }
                else{
                    throw new IllegalArgumentException("삭제 권한이 없습니다.");
                }
            } else if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("ADMIN")) {
                postRepository.deleteById(id);
                return id;
            }
        }
        return -1L;
    }

}
