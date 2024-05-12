package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form);
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity(); // DTO인 form 객체를 엔티티 객체로 변환하는 역할
        System.out.println(article);
        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article); // article 엔티티를 저장해  saved 객체에 반환
        System.out.println(saved);
        return "";
    }
}
