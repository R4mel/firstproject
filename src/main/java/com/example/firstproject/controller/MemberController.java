package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class MemberController {
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/members/new")
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
        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = {}", id);
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String members(Model model) {
        List<Member> memberEntityList = memberRepository.findAll();
        model.addAttribute("memberList", memberEntityList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        // 모델에 데이터 등록하기
        model.addAttribute("member", memberEntity);
        // 뷰 페이지 설정
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form) {
        log.info(form.toString());
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if (target != null) {
            memberRepository.save(memberEntity);
        }
        return "redirect:/members/" + memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
//        1. 삭제할 대상 가져오기
        Member target = memberRepository.findById(id).orElse(null);
        assert target != null;
        log.info(target.toString());
//        2. 대상 엔티티 삭제하기
        memberRepository.delete(target);
        rttr.addFlashAttribute("msg", "삭제됐습니다!");
//        3. 결과 페이지로 리다이렉트하기
        return "redirect:/members";
    }
}
