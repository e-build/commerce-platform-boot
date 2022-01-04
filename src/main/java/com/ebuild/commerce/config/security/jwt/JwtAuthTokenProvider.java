package com.ebuild.commerce.config.security.jwt;

import com.ebuild.commerce.business.user.commerceUserDetail.service.CommerceUserService;
import com.ebuild.commerce.config.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthTokenProvider implements AuthTokenProvider<AuthToken<Claims>>{

  @Value("${jwt.secret-key}")
  private String secretKey;
  @Value("${jwt.auth-token-valid-minute}")
  private String tokenValidMinute;

//  private final CommerceUserService commerceUserService;

  @Override
  public JwtAuthToken createAuthToken(Long userId, String role) {
    return new JwtAuthToken(String.valueOf(userId), role, tokenValidMinute, secretKey);
  }

  @Override
  public JwtAuthToken convertAuthToken(String token) {
    return new JwtAuthToken(token, secretKey);
  }

  @Override
  public Authentication getAuthentication(AuthToken<Claims> authToken) {
    Claims claims = authToken.getData();
    Collection<? extends GrantedAuthority> authorities = resolveAuthorities(claims);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(claims.getSubject(), authToken, authorities);

    // TODO: Redis 에 사용자 정보 캐싱 및 Authentication 객체 생성 로직 추가
//  CommerceUserDetail commerUserDetail = userDetailRedisTemplate.get(claims.getSubject());
//    if ( commerUserDetail == null ){
//      commerUserDetail = commerceUserService.findById(claims.getSubject());
//      commerUserDetail.lazyLoadUserByRole();
//      userDetailRedisTemplate.set(commerUserDetail);
//    }
//    usernamePasswordAuthenticationToken.setDetails(commerUserDetail);
    return usernamePasswordAuthenticationToken;
  }

  private List<SimpleGrantedAuthority> resolveAuthorities(Claims claims) {
    return Arrays.stream(new String[]{claims.get(SecurityConstants.AUTHORITIES_KEY).toString()})
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<String> resolveAuthTokenFromHeader(HttpServletRequest request) {
    String authToken = request.getHeader(SecurityConstants.AUTH_HEADER);
    if (StringUtils.isNotBlank(authToken))
      return Optional.of(authToken);
    return Optional.empty();
  }

}
