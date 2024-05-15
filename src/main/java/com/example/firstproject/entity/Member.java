package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // lombok: 생성자
@NoArgsConstructor
@Getter
@ToString // lombok: toString
@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String email;

    @Column
    private String password;
}
