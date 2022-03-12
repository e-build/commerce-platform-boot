package com.ebuild.commerce.business.auth.domain.dto;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.oauth.token.JWTProvider;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResDto {

  private final Long id;
  private final String email;
  private final String nickname;
  private final TokenDto token;

  @Builder
  public LoginResDto(Long id, String email,
      TokenDto token, String nickname) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.token = token;
  }

  public static LoginResDto of(JWTProvider jwtProvider, AppUserDetails appUserDetails) {
    return LoginResDto.builder()
        .email(appUserDetails.getEmail())
        .nickname(appUserDetails.getNickname())
        .token(TokenDto.builder()
            .accessToken(jwtProvider.createAccessToken(appUserDetails.getEmail(), appUserDetails.mapRoleToString()).getToken())
            .refreshToken(jwtProvider.createRefreshToken(appUserDetails.getEmail(), appUserDetails.mapRoleToString()).getToken())
            .build())
        .build();
  }
}
