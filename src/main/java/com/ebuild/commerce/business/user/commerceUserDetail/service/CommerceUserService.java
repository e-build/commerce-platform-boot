package com.ebuild.commerce.business.user.commerceUserDetail.service;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserLoginReqDto;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import com.ebuild.commerce.common.RedisService;
import com.ebuild.commerce.config.security.jwt.JwtTokenProvider;
import com.ebuild.commerce.config.security.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommerceUserService implements UserDetailsService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtAuthTokenProvider;
  private final RedisService redisService;

  private final CommerceUserDetailRepository commerceUserDetailRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return commerceUserDetailRepository
        .findOneByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("[" + email + "] 대한 계정은 존재하지 않습니다"));
  }

  public TokenDto login(CommerceUserLoginReqDto commerceUserLoginReqDto) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                commerceUserLoginReqDto.getUsername()
                , commerceUserLoginReqDto.getPassword()
            )
    );

    TokenDto token = TokenDto.of(authentication, jwtAuthTokenProvider);
    CommerceUserDetail commerceUserDetail = (CommerceUserDetail) authentication.getPrincipal();
    redisService.setRefreshToken(commerceUserDetail, token.getRefreshToken());
    redisService.setUserDetail(commerceUserDetail);

    return token;
  }

  public void logout(CommerceUserDetail commerceUserDetail){
    redisService.removeRefreshToken(commerceUserDetail);
    redisService.removeUserDetail(commerceUserDetail);
  }

}
