package com.jungle.springpost.entity;

import com.jungle.springpost.dto.PostRequestDto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String writer;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	@OrderBy("createdAt desc")
	List<PostComment> commentList = new ArrayList<>();
	//    @Column(nullable = false)
	//    private String password;

	public Post(PostRequestDto requestDto) {
		this.writer = requestDto.getUsername();
		this.title = requestDto.getTitle();
		this.contents = requestDto.getContents();
	}

	public void update(PostRequestDto postRequestDto) {
		//        this.writer = postRequestDto.getUsername();
		this.title = postRequestDto.getTitle();
		this.contents = postRequestDto.getContents();
	}

}
