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

        if(jwtUtil.validateToken(token)){ //유효한 토큰일 경우에만 게시글 작성가능
            requestDto.setUsername(jwtUtil.getUserInfoFromToken(token).getSubject());
            Post post = new Post(requestDto);
            postRepository.save(post);
            return post;
        }
//        Post post = new Post(requestDto);
//        postRepository.save(post);
        return null;
    }

    @Transactional(readOnly = true)
    public List<PostSummary> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public PostSimpleDto getPostsById(Long id) {
        Post p = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        PostSimpleDto postSimpleDto = new PostSimpleDto();

        postSimpleDto.setContents(p.getContents());
        postSimpleDto.setTitle(p.getTitle());
        postSimpleDto.setWriter(p.getWriter());
        postSimpleDto.setCreatedAt(p.getCreatedAt());

        return postSimpleDto;
    }

    @Transactional
    public Long update(Long id, PostRequestDto requestDto, HttpServletRequest request){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글 입니다."));

        String token = jwtUtil.resolveToken(request);

        if(jwtUtil.validateToken(token)) { //토큰 유효성 검사
            if(jwtUtil.getUserInfoFromToken(token).get("auth").equals("USER")) {
                if (post.getWriter().equals(jwtUtil.getUserInfoFromToken(token).getSubject())) { //해당 사용자 인지
                    post.update(requestDto);
                    return post.getId();
                }
                else{

                    return 0L;
                }
            } else if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("ADMIN")) {
                post.update(requestDto);
                return post.getId();
            }
        }
        return 0L;
    }

    @Transactional
    public Long deletePost(Long id, HttpServletRequest request){
        Long err = 0L;
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

                    return 0L;
                }
            } else if (jwtUtil.getUserInfoFromToken(token).get("auth").equals("ADMIN")) {
                postRepository.deleteById(id);
                return id;
            }
        }
        return err;
    }

}
