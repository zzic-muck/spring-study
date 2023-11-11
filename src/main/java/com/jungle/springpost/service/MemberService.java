package com.jungle.springpost.service;

import com.jungle.springpost.dto.LoginRequestDto;
import com.jungle.springpost.dto.SignupRequestDto;
import com.jungle.springpost.entity.Member;
import com.jungle.springpost.entity.UserRoleEnum;
import com.jungle.springpost.jwt.JwtUtil;
import com.jungle.springpost.repository.MemberRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	// ADMIN_TOKEN
	private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

	@Transactional
	public void signup(SignupRequestDto signupRequestDto) {
		String username = signupRequestDto.getUsername();
		String password = signupRequestDto.getPassword();
		password = passwordEncoder.encode(password);
		String email = signupRequestDto.getEmail();

		// 회원 중복 확인
		Optional<Member> found = memberRepository.findByUsername(username);
		if (found.isPresent()) {
			throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
			//            throw new IllegalArgumentException();
		}

		// 사용자 ROLE 확인
		UserRoleEnum role = UserRoleEnum.USER;
		System.out.println(signupRequestDto.isAdmin());
		if (signupRequestDto.isAdmin()) {

			if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
				throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
			}
			role = UserRoleEnum.ADMIN;
		}
		Member member = new Member(username, password, email, role);
		memberRepository.save(member);
	}

	@Transactional(readOnly = true)
	public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
		String username = loginRequestDto.getUsername();
		String password = loginRequestDto.getPassword();

		// 사용자 확인
		Member member = memberRepository.findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException("등록된 사용자가 없습니다.")
		);

		//암호화된 비밀번호 확인
		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getRole()));
	}
	//댓글 기능
	//    @Transactional
	//    public String comment(@RequestBody){
	//
	//
	//        return comment;
	//    }

}

