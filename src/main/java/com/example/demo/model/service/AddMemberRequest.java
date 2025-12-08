package com.example.demo.model.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*; // 어노테이션 자동 생성
import com.example.demo.model.domain.Member;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // getter, setter, toString, equals 등 자동 생성

public class AddMemberRequest {
  @NotBlank(message = "이름은 비울 수 없습니다.")
  @Pattern(regexp = "^[A-Za-z가-힣0-9]+$", message = "이름에는 특수문자를 사용할 수 없습니다.")
  private String name;

  @NotBlank(message = "이메일은 비울 수 없습니다.")
  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호는 비울 수 없습니다.")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "비밀번호는 대소문자, 숫자를 포함해 8자 이상이어야 합니다.")
  private String password;

  @NotBlank(message = "나이는 비울 수 없습니다.")
  @Pattern(regexp = "^(19|[2-8]\\d|90)$", message = "나이는 19세 이상 90세 이하만 가능합니다.")
  private String age;

  private String mobile;  // 모바일, 주소는 공백 허용
  private String address;

  public Member toEntity(){ // Member 생성자를 통해 객체 생성
    return Member.builder()
      .name(name)
      .email(email)
      .password(password)
      .age(age)
      .mobile(mobile)
      .address(address)
      .build();
    }
}