package com.ebuild.commerce.business.auth.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {

  private final String accessToken;
  private final String refreshToken;

  @Builder
  public TokenDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
