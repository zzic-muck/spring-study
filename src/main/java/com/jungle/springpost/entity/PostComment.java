package com.jungle.springpost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jungle.springpost.dto.PostCommentDto;
import com.jungle.springpost.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
public class PostComment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String username;

    @Column
    public String comment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public PostComment(PostCommentDto commentDto, Post post) {
        this.username = commentDto.getUsername();
        this.comment = commentDto.getComment();
        this.post = post;
    }

    public void Commentupdate(PostCommentDto commentDto) {

        this.comment = commentDto.getComment();
    }
}

