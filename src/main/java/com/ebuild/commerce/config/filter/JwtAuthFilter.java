package com.ebuild.commerce.config.filter;

import com.ebuild.commerce.config.security.jwt.AuthToken;
import com.ebuild.commerce.config.security.jwt.JwtAuthTokenProvider;
import com.ebuild.commerce.exception.security.JwtTokenInvalidException;
import com.ebuild.commerce.exception.security.JwtTokenNotExistsException;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.Arrays;
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

  public JwtAuthFilter(JwtAuthTokenProvider jwtAuthTokenProvider){
    this.jwtAuthTokenProvider = jwtAuthTokenProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request
      , HttpServletResponse response
      , FilterChain filterChain ) throws ServletException, IOException {

    String requestUri = request.getRequestURI();

    Optional<String> token = jwtAuthTokenProvider.resolveAuthToken(request);
    if (token.isPresent()){
      AuthToken<Claims> authToken = jwtAuthTokenProvider.convertAuthToken(token.get());
      if (authToken.validate()) {
        Authentication authentication = jwtAuthTokenProvider.getAuthentication(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }

}
