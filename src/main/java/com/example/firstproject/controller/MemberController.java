package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/signup")
    public String signUp() {
        return "members/new";
    }

    @PostMapping("/join")
    public String signUp(MemberForm memberForm) {
        log.info(memberForm.toString());
        Member member = memberForm.toEntity(); // DTO -> Entity
        log.info(member.toString());
        Member saved = memberRepository.save(member);// DB에 엔티티 저장
        log.info(saved.toString());
        return "";
    }
}
