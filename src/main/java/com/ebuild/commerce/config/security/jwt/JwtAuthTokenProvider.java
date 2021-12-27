package com.ebuild.commerce.config.security.jwt;

import com.ebuild.commerce.config.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthTokenProvider implements AuthTokenProvider<AuthToken<Claims>>{

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Override
  public JwtAuthToken createAuthToken(String userId, String role, Date expiredDate) {
    return new JwtAuthToken(userId, role, expiredDate, secretKey);
  }

  @Override
  public JwtAuthToken convertAuthToken(String token) {
    return new JwtAuthToken(token, secretKey);
  }

  @Override
  public Authentication getAuthentication(AuthToken<Claims> authToken) {
    Claims claims = authToken.getData();
    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(new String[]{claims.get(SecurityConstants.AUTHORITIES_KEY).toString()})
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(claims.getSubject(), authToken, authorities);
  }

  @Override
  public Optional<String> resolveAuthToken(HttpServletRequest request) {
    String authToken = request.getHeader(SecurityConstants.AUTH_HEADER);
    if (StringUtils.isNotBlank(authToken))
      return Optional.of(authToken);
    return Optional.empty();
  }

}
