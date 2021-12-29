package com.ebuild.commerce.business.user.commerceUserDetail.service;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserLoginReqDto;
import com.ebuild.commerce.config.security.jwt.JwtAuthTokenProvider;
import com.ebuild.commerce.config.security.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommerceUserService {

  private final AuthenticationManager authenticationManager;
  private final JwtAuthTokenProvider jwtAuthTokenProvider;

  public TokenDto login(CommerceUserLoginReqDto commerceUserLoginReqDto) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                commerceUserLoginReqDto.getUsername()
                , commerceUserLoginReqDto.getPassword()
            )
    );

    return TokenDto.of(authentication, jwtAuthTokenProvider);
  }
}
