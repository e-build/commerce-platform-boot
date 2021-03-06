package com.ebuild.commerce.business.auth.service;

import static org.apache.commons.lang3.StringUtils.*;

import com.ebuild.commerce.business.auth.domain.ReAuthReasonType;
import com.ebuild.commerce.business.auth.controller.dto.LoginReqDto;
import com.ebuild.commerce.business.auth.controller.dto.LoginResDto;
import com.ebuild.commerce.business.auth.controller.dto.TokenDto;
import com.ebuild.commerce.business.auth.domain.entity.AppRefreshToken;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.repository.JpaRefreshTokenRepository;
import com.ebuild.commerce.business.buyer.service.BuyerQueryService;
import com.ebuild.commerce.common.RedisService;
import com.ebuild.commerce.exception.security.JwtTokenInvalidException;
import com.ebuild.commerce.exception.security.JwtTokenNotExistsException;
import com.ebuild.commerce.exception.security.ReAuthenticateRequiredException;
import com.ebuild.commerce.oauth.token.JWT;
import com.ebuild.commerce.oauth.token.JWTProvider;
import io.jsonwebtoken.ExpiredJwtException;
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
public class CommerceAuthService {

  private final RedisService redisService;
  private final AuthenticationManager authenticationManager;
  private final BuyerQueryService buyerQueryService;
  private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
  private final JWTProvider jwtProvider;

  @Transactional
  public LoginResDto login(LoginReqDto loginReqDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginReqDto.getUsername()
            , loginReqDto.getPassword()
        )
    );

    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

    LoginResDto loginResDto = LoginResDto.of(jwtProvider, appUserDetails);

    // Refresh ?????? ??????
    String refreshToken = loginResDto.getToken().getRefreshToken();
    AppRefreshToken appRefreshToken = jpaRefreshTokenRepository.findByEmail(
            appUserDetails.getEmail())
        .orElseGet(() ->
            jpaRefreshTokenRepository.save(
                new AppRefreshToken(appUserDetails, refreshToken))
        );
    appRefreshToken.setRefreshToken(refreshToken);

    return loginResDto;
  }

  public void logout(AppUserDetails appUserDetails) {
    redisService.removeRefreshToken(appUserDetails);
    redisService.removeUser(appUserDetails);
  }

  public LoginResDto oauthLogin(String token) {
    JWT jwt = jwtProvider.convertAuthToken(token);
    if (!jwt.validate()) {
      throw new JwtTokenInvalidException("JWT ????????? ???????????? ????????????.");
    }

    AppUserDetails appUserDetails = buyerQueryService
        .findByEmail(jwt.resolveOAuthLoginSuccessTokenEmail())
        .getAppUserDetails();
    LoginResDto loginResDto = LoginResDto.of(jwtProvider, appUserDetails);

    // Refresh ?????? ??????
    jpaRefreshTokenRepository.save(
        new AppRefreshToken(appUserDetails, loginResDto.getToken().getRefreshToken())
    );
//    redisService.setRefreshToken(appUserDetails, loginResDto.getToken().getRefreshToken());
    return loginResDto;
  }

  public TokenDto issueToken(String accessToken, String refreshToken) {
    if (accessToken == null || refreshToken == null) {
      throw new JwtTokenNotExistsException();
    }

    JWT accessJwt = jwtProvider.convertAuthToken(accessToken);
    JWT refreshJwt = jwtProvider.convertAuthToken(refreshToken);

    // Access Token ???????????? ?????? ??????
    try {
      accessJwt.validate();
    } catch (ExpiredJwtException ignored) {

    } catch (Exception e) {
      throw new ReAuthenticateRequiredException(ReAuthReasonType.INVALID_TOKEN);
    }

    // ?????? Refresh Token ????????? ??????
    try {
      refreshJwt.validate();
    } catch (ExpiredJwtException ignored) {
      throw new ReAuthenticateRequiredException(ReAuthReasonType.EXPIRED_REFRESH_TOKEN);
    } catch (Exception e) {
      throw new ReAuthenticateRequiredException(ReAuthReasonType.INVALID_TOKEN);
    }

    // ?????????????????? Refresh Token ??????
    String email = refreshJwt.resolveEmail();
    AppRefreshToken savedAppRefreshToken = jpaRefreshTokenRepository.findByEmail(email)
        .orElseThrow(() -> new ReAuthenticateRequiredException(ReAuthReasonType.NO_TOKEN_STORED));

    // Refresh Token ???????????? ??????
    if (!equalsIgnoreCase(refreshToken, savedAppRefreshToken.getRefreshToken())) {
      throw new ReAuthenticateRequiredException(ReAuthReasonType.TOKENS_DO_NOT_MATCH);
    }

    return TokenDto.builder()
        .accessToken(
            jwtProvider.createAccessToken(email, refreshJwt.resolveRoleStringList()).getToken())
        .refreshToken(refreshToken)
        .build();
  }
}
