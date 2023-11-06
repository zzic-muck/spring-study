package com.jungle.springpost.controller;



import com.jungle.springpost.dto.LoginRequestDto;
import com.jungle.springpost.dto.LoginResponseDto;
import com.jungle.springpost.dto.SignupRequestDto;
import com.jungle.springpost.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){ //에러가 있으면
            throw new IllegalArgumentException("ID 또는 PASSWORD가 조건에 맞지 않습니다.");
        }

        memberService.signup(signupRequestDto);
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .message(signupRequestDto.getUsername() + " 님 가입을 축하합니다")
                .statusCode(200)
                .build();
        return ResponseEntity.ok().body(loginResponseDto);
    }
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .message(loginRequestDto.getUsername() + " 로그인 성공")
                .statusCode(200)
                .build();
        return ResponseEntity.ok().body(loginResponseDto);
    }

}