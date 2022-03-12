package com.ebuild.commerce.business.auth.service;

import com.ebuild.commerce.business.auth.domain.dto.LoginReqDto;
import com.ebuild.commerce.business.auth.domain.dto.LoginResDto;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.common.RedisService;
import com.ebuild.commerce.exception.security.JwtTokenInvalidException;
import com.ebuild.commerce.oauth.token.JWT;
import com.ebuild.commerce.oauth.token.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommerceAuthService {

  private final RedisService redisService;
  private final AuthenticationManager authenticationManager;
  private final JWTProvider jwtProvider;
  private final AppUserDetailsQueryService appUserDetailsQueryService;

  public LoginResDto login(LoginReqDto loginReqDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginReqDto.getUsername()
            , loginReqDto.getPassword()
        )
    );

    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

    LoginResDto loginResDto = LoginResDto.of(jwtProvider, appUserDetails);

    redisService.setRefreshToken(appUserDetails, loginResDto.getToken().getRefreshToken());
    redisService.setUser(appUserDetails);

    return loginResDto;
  }

  public void logout(AppUserDetails appUserDetails){
    redisService.removeRefreshToken(appUserDetails);
    redisService.removeUser(appUserDetails);
  }

  public LoginResDto oauthLogin(String token) {
    JWT jwt = jwtProvider.convertAuthToken(token);
    if (!jwt.validate())
      throw new JwtTokenInvalidException();

    AppUserDetails appUserDetails = appUserDetailsQueryService.findByEmail(jwt.resolveOAuthLoginSuccessTokenEmail());
    LoginResDto loginResDto = LoginResDto.of(jwtProvider, appUserDetails);

    // Refresh 토큰 저장
    redisService.setRefreshToken(appUserDetails, loginResDto.getToken().getRefreshToken());
    redisService.setUser(appUserDetails);

    return loginResDto;
  }

}
