package com.ebuild.commerce.business.user.commerceUserDetail.service;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserLoginReqDto;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import com.ebuild.commerce.config.security.jwt.JwtAuthTokenProvider;
import com.ebuild.commerce.config.security.jwt.TokenDto;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommerceUserService {

//  private final CommerceUserDetailRepository commerceUserDetailRepository;
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
