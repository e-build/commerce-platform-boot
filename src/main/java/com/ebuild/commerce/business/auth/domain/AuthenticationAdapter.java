package com.ebuild.commerce.business.auth.domain;

import lombok.Getter;

@Getter
public class AuthenticationAdapter {

  private final UserSubject userSubject;

  public AuthenticationAdapter(UserSubject userSubject){
    this.userSubject = userSubject;
  }

}
