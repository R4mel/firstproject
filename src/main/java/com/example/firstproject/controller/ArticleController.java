package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j // lombok: 로깅, Simple Logging Facade for Java
@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    public ArticleController(ArticleRepository articleRepository, CommentService commentService) {
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
//        System.out.println(form);
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity(); // DTO인 form 객체를 엔티티 객체로 변환하는 역할
        log.info(article.toString());
//        System.out.println(article);
        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article); // article 엔티티를 저장해  saved 객체에 반환
        log.info(saved.toString());
//        System.out.println(saved);
        return "redirect:/articles/" + saved.getId(); // 리다이렉트를 작성할 위치
    }

    @GetMapping("/articles/{id}") // 데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model) { // 매개변수로 id 받아오기
        log.info("id = {}", id); // id를 잘 받았는지 확인하는 로그 찍기
//      Optional<Article> articleEntity = articleRepository.findById(id);
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        // id로 DB에서 조회한 데이터는 모델에 article이라는 이름으로 등록
        model.addAttribute("commentDtos", commentDtos);
        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. DB에서 모든 Article 데이터 가져오기
//        List<Article> articleEntityList = (List<Article>) articleRepository.findAll();
//        Iterable<Article> articleEntityList = articleRepository.findAll();
        List<Article> articleEntityList = articleRepository.findAll();
        // 2. 가져온 Article 묶음을 모델에 등록하기
        model.addAttribute("articleList", articleEntityList); // articleEntityList 등록
        // 3. 사용자에게 보여줄 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) { // 매개변수로 DTO 받아 오기
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2. 엔티티를 DB에 저장하기
        // DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티를 DB에 저장(갱신)
        }
        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        assert target != null; // 코드의 논리적인 조건이 올바르게 사용됐는지 확인
        log.info(target.toString());
        // 2. 대상 엔티티 삭제하기
        articleRepository.delete(target);
        rttr.addFlashAttribute("msg", "삭제됐습니다!");
        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
