package com.jungle.springpost.controller;

import com.jungle.springpost.entity.Member;
import com.jungle.springpost.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;

    @PostMapping("/api/register")
    public Member registerMember(@RequestBody Member member){
        return memberService.registerMember(member);
    }
}
