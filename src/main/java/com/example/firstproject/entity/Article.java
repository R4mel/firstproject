package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // lombok: 생성자
@ToString // lombok: toString
@Entity // JPA에서 제공하는 어노테이션, 이 어노테이션이 붙은 클래스를 기반으로 DB에 테이블 생성
public class Article {
    @Id // 대푯값
    @GeneratedValue // 대푯값을 자동으로 생성 및 증가
    private Long id;

    @Column // DB 테이블의 각 열과 연결
    private String title;

    @Column // DB 테이블의 각 열과 연결
    private String content;
}
