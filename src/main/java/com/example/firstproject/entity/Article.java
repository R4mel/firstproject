package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor // lombok: 생성자
@NoArgsConstructor // lombok: 기본 생성자
@ToString // lombok: toString
@Entity // JPA에서 제공하는 어노테이션, 이 어노테이션이 붙은 클래스를 기반으로 DB에 테이블 생성
@Getter // lombok: getter
public class Article {
    @Id // 대푯값
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 대푯값을 자동으로 생성 및 증가
    private Long id;

    @Column // DB 테이블의 각 열과 연결
    private String title;

    @Column // DB 테이블의 각 열과 연결
    private String content;

    public void patch(Article article) {
        if (article.title != null) {
            this.title = article.title;
        }
        if (article.content != null) {
            this.content = article.content;
        }
    }
}
