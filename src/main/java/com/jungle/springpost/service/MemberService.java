package com.jungle.springpost.service;

import com.jungle.springpost.entity.Member;
import com.jungle.springpost.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member registerMember(Member member){
        member.setPassword(member.getPassword());
        return memberRepository.save(member);
    }

//    @Transactional


}