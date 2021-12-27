package com.ebuild.commerce.config.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Builder
@Getter
@Setter
public class TokenDto {

  private final String authenticationToken;
  private final String refreshToken;

  public static TokenDto of(Authentication authentication){
    return TokenDto.builder()
        .authenticationToken(String.valueOf(authentication.getCredentials()))
//        .refreshToken()
        .build();
  }

}
