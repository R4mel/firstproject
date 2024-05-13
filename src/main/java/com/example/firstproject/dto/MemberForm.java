package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // lombok: 생성자
@ToString // lombok: toString
public class MemberForm {
    private String email;

    private String password;

    public Member toEntity() {
        return new Member(null, email, password);
    }
}
