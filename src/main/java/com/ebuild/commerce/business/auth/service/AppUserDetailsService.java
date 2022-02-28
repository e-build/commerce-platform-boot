package com.ebuild.commerce.business.auth.service;

import com.ebuild.commerce.business.auth.domain.dto.LoginReqDto;
import com.ebuild.commerce.business.auth.domain.dto.TokenDto;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import com.ebuild.commerce.common.RedisService;
import com.ebuild.commerce.oauth.domain.UserPrincipal;
import com.ebuild.commerce.oauth.token.JWTProvider;
import java.util.Date;
import java.util.stream.Collectors;
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

    TokenDto token = TokenDto.builder()
        .authenticationToken(
            jwtProvider.createAuthToken(
                String.valueOf(appUserDetails.getEmail()),
                appUserDetails.roleListString(),
                new Date()
            ).getToken()
        )
        .refreshToken(
            jwtProvider.createAuthToken(
                String.valueOf(appUserDetails.getEmail()),
                appUserDetails.roleListString(),
                new Date()
            ).getToken()
        )
        .build();


    redisService.setRefreshToken(appUserDetails, token.getRefreshToken());
    redisService.setUserDetail(appUserDetails);

    return token;
  }

  public void logout(AppUserDetails appUserDetails){
    redisService.removeRefreshToken(appUserDetails);
    redisService.removeUserDetail(appUserDetails);
  }

}
