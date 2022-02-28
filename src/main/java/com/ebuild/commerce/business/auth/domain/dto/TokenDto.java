package com.ebuild.commerce.business.auth.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {

  private final String authenticationToken;
  private final String refreshToken;

  @Builder
  public TokenDto(String authenticationToken, String refreshToken) {
    this.authenticationToken = authenticationToken;
    this.refreshToken = refreshToken;
  }
}
