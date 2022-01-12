package com.ebuild.commerce.config.filter;

import com.ebuild.commerce.config.security.SecurityConstants;
import com.ebuild.commerce.config.security.jwt.AuthToken;
import com.ebuild.commerce.config.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtAuthTokenProvider;

  public JwtAuthFilter(JwtTokenProvider jwtAuthTokenProvider) {
    this.jwtAuthTokenProvider = jwtAuthTokenProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request
      , HttpServletResponse response
      , FilterChain filterChain) throws ServletException, IOException {
    Optional<String> authJwtToken = jwtAuthTokenProvider.resolveAuthTokenFromHeader(request);
    Optional<String> refreshJwtToken = jwtAuthTokenProvider.resolveRefreshTokenFromHeader(request);

    if (authJwtToken.isPresent()) {
      AuthToken<Claims> authToken = jwtAuthTokenProvider.convertJWT(authJwtToken.get());

      // Refresh Token 검증
      if (authToken.validate()) {
        Authentication authentication = jwtAuthTokenProvider.getAuthentication(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {

        // Refresh Token 검증
//        if (refreshJwtToken.isPresent()) {
//          AuthToken<Claims> refreshToken = jwtAuthTokenProvider.convertJWT(refreshJwtToken.get());
//          if (refreshToken.validate()) {
//            Authentication authentication = jwtAuthTokenProvider.getAuthentication(refreshToken);
//            response.setHeader(SecurityConstants.AUTH_TOKEN_HEADER,"");
//            response.setHeader(SecurityConstants.REFRESH_TOKEN_HEADER,"");
//
//          }
//        }

      }
    }
    filterChain.doFilter(request, response);
  }

}
