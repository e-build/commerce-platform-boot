package com.ebuild.commerce.config.security.jwt;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Getter
@Setter
public class TokenDto {

  private final String authenticationToken;
  private final String refreshToken;

  public static TokenDto of(Authentication authentication, JwtTokenProvider jwtAuthTokenProvider){
    CommerceUserDetail principal = (CommerceUserDetail) authentication.getPrincipal();

    String roles = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    return TokenDto.builder()
        .authenticationToken(jwtAuthTokenProvider.createAuthJWT(principal.getId(), roles).getToken())
        .refreshToken(jwtAuthTokenProvider.createRefreshJWT(principal.getId()).getToken())
        .build();
  }

}
