package com.ebuild.commerce.business.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSubject {

  private final String email;

  @Builder
  public UserSubject(String email) {
    this.email = email;
  }

  public static UserSubject of(String resolveEmail) {
    return UserSubject.builder()
        .email(resolveEmail)
        .build();
  }
}
