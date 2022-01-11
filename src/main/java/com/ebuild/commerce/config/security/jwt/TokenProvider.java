package com.ebuild.commerce.config.security.jwt;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface TokenProvider<T> {

  T createAuthJWT(Long userId, String role);

  T createRefreshJWT(Long userId);

  T convertJWT(String token);

  Authentication getAuthentication(T authToken);

  Optional<String> resolveAuthTokenFromHeader(HttpServletRequest request);

  Optional<String> resolveRefreshTokenFromHeader(HttpServletRequest request);
}
