package com.ebuild.commerce.config.security.jwt;

import java.util.Date;
import java.util.Optional;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthTokenProvider<T> {
  T createAuthToken(String id, String role, Date expiredDate);
  T convertAuthToken(String token);
  Authentication getAuthentication(T authToken);
  Optional<String> resolveAuthToken(HttpServletRequest request);

}
