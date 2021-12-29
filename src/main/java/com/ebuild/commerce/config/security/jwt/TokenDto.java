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

  public static TokenDto of(Authentication authentication, JwtAuthTokenProvider jwtAuthTokenProvider){
    CommerceUserDetail principal = (CommerceUserDetail) authentication.getPrincipal();

    String roles = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.createAuthToken(principal.getId(), roles);
    String jwt = jwtAuthToken.getToken();

    return TokenDto.builder()
        .authenticationToken(jwt)
//        .refreshToken()
        .build();
  }

}
