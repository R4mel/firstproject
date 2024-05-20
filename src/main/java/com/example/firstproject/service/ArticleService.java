package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article);
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null || !id.equals(article.getId())) {
            log.info("잘못된 요청! id: {}, article: {}", id, article);
            return null;
        }
        target.patch(article);
        return articleRepository.save(target);
    }


    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 엔티티 묶음으로 변환하기
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());
//        List<Article> articleList = dtos.stream().map(ArticleForm::toEntity).toList();
//        List<Article> articleList = new ArrayList<>();
//        for(int i=0; i<dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

        // 2. 엔티티 묶음을 DB에 저장하기
        articleList.stream().forEach(article -> articleRepository.save(article));
//        for(int i=0; i<articleList.size(); i++) {
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }

        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("결제 실패!"));

        return articleList;
    }
}
