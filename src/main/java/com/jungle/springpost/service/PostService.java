package com.jungle.springpost.service;

import com.jungle.springpost.dto.PostRequestDto;
import com.jungle.springpost.dto.PostSimpleDto;
import com.jungle.springpost.dto.PostSummary;
import com.jungle.springpost.entity.Post;
import com.jungle.springpost.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostRequestDto requestDto){
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

        PostSimpleDto postSimpleDto = new PostSimpleDto();

        postSimpleDto.setContents(p.getContents());
        postSimpleDto.setTitle(p.getTitle());
        postSimpleDto.setWriter(p.getWriter());
        postSimpleDto.setCreatedAt(p.getCreatedAt());

        return postSimpleDto;
    }

    @Transactional
    public Long update(Long id, PostRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 없어요.")
        );
        post.update(requestDto);
        return post.getId();
    }

    @Transactional
    public Long deletePost(Long id){
        postRepository.deleteById(id);
        return id;
    }

}
