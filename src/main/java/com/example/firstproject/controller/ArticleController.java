package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j // lombok: 로깅, Simple Logging Facade for Java
@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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
    public String show(@PathVariable long id, Model model) { // 매개변수로 id 받아오기
        log.info("id = {}", id); // id를 잘 받았는지 확인하는 로그 찍기
//      Optional<Article> articleEntity = articleRepository.findById(id);
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity); // id로 DB에서 조회한 데이터는 모델에 article이라는 이름으로 등록
        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. DB에서 모든 Article 데이터 가져오기
//        List<Article> articleEntityList = (List<Article>) articleRepository.findAll();
//        Iterable<Article> articleEntityList = articleRepository.findAll();
        List<Article> articleEntityList = articleRepository.findAll();
        // 2. 가져온 Article 묶음을 모델에 등록하기
        model.addAttribute("articleList", articleEntityList); // articleEntityList 등록
        // 3. 사용자에게 보여줄 뷰 페이지 설정하기
        return "articles/index";
    }
}
