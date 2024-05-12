package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signUp() {
        return "members/new";
    }

    @PostMapping("/join")
    public String signUp(MemberForm memberForm) {
        Member member = memberForm.toEntity(); // DTO -> Entity
        Member saved = memberRepository.save(member);// DB에 엔티티 저장
        return "";
    }
}
