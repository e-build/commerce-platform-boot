package com.ebuild.commerce.business.auth.service;

import com.ebuild.commerce.business.auth.domain.dto.LoginReqDto;
import com.ebuild.commerce.business.auth.domain.dto.TokenDto;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import com.ebuild.commerce.common.RedisService;
import com.ebuild.commerce.oauth.token.JWTProvider;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppUserDetailsService{

  private final RedisService redisService;
  private final AuthenticationManager authenticationManager;
  private final JWTProvider jwtProvider;
  private final JpaAppUserDetailsRepository jpaAppUserDetailsRepository;

  public TokenDto login(LoginReqDto loginReqDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginReqDto.getUsername()
            , loginReqDto.getPassword()
        )
    );

    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

    List<String> roleStringList = appUserDetails.getRoleList()
        .stream()
        .map(appUserRole -> appUserRole.getRole().getName().getCode())
        .collect(Collectors.toList());

    TokenDto token = TokenDto.builder()
        .authenticationToken(
            jwtProvider.createAccessToken(String.valueOf(appUserDetails.getEmail()), roleStringList).getToken())
        .refreshToken(
            jwtProvider.createRefreshToken(String.valueOf(appUserDetails.getEmail()), roleStringList).getToken())
        .build();


    redisService.setRefreshToken(appUserDetails, token.getRefreshToken());
    redisService.setUser(appUserDetails);

    return token;
  }

  public void logout(AppUserDetails appUserDetails){
    redisService.removeRefreshToken(appUserDetails);
    redisService.removeUser(appUserDetails);
  }

}
