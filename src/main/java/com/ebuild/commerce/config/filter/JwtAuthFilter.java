package com.ebuild.commerce.config.filter;

import com.ebuild.commerce.config.security.jwt.AuthToken;
import com.ebuild.commerce.config.security.jwt.JwtAuthTokenProvider;
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

  private final JwtAuthTokenProvider jwtAuthTokenProvider;

  public JwtAuthFilter(JwtAuthTokenProvider jwtAuthTokenProvider) {
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
      AuthToken<Claims> authToken = jwtAuthTokenProvider.convertAuthToken(authJwtToken.get());

      // Refresh Token 검증
      if (authToken.validate()) {
        Authentication authentication = jwtAuthTokenProvider.getAuthentication(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {

        // Refresh Token 검증
        AuthToken<Claims> refreshToken = jwtAuthTokenProvider.convertAuthToken(refreshJwtToken.get());
        if( refreshToken.validate() ){
          Authentication authentication = jwtAuthTokenProvider.getAuthentication(refreshToken);

        }

      }


    }
    filterChain.doFilter(request, response);
  }

}
