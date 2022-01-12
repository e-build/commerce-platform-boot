package com.ebuild.commerce.config.security.jwt;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.CommerceUserAdapter;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.service.CommerceUserQueryService;
import com.ebuild.commerce.common.RedisService;
import com.ebuild.commerce.config.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider<AuthToken<Claims>> {

  @Value("${jwt.secret-key}")
  private String secretKey;
  @Value("${jwt.auth-token-valid-minute}")
  private String authTokenValidMinute;
  @Value("${jwt.refresh-token-valid-minute}")
  private String refreshTokenValidMinute;

  private final CommerceUserQueryService commerceUserQueryService;
  private final RedisService redisService;

  @Override
  public JWT createAuthJWT(Long userId, String role) {
    return new JWT(String.valueOf(userId), role, authTokenValidMinute, secretKey);
  }

  @Override
  public JWT createRefreshJWT(Long userId) {
    return new JWT(String.valueOf(userId), null, refreshTokenValidMinute, secretKey);
  }

  @Override
  public JWT convertJWT(String token) {
    return new JWT(token, secretKey);
  }

  @Override
  public Authentication getAuthentication(AuthToken<Claims> authToken) {
    Claims claims = authToken.getData();

    // 사용자 정보 레디스에 캐싱
    long userDetailId = Long.parseLong(claims.getSubject());
    CommerceUserDetail commerceUserDetail = redisService.getCommerceUserDetail(userDetailId);

    commerceUserDetail.passwordMasking();

    return new UsernamePasswordAuthenticationToken(
        new CommerceUserAdapter(commerceUserDetail)
        , authToken
        , resolveAuthorities(claims)
    );
  }

  private List<SimpleGrantedAuthority> resolveAuthorities(Claims claims) {
    return Arrays.stream(new String[]{claims.get(SecurityConstants.AUTHORITIES_KEY).toString()})
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<String> resolveAuthTokenFromHeader(HttpServletRequest request) {
    String token = request.getHeader(SecurityConstants.AUTH_TOKEN_HEADER);
    if (StringUtils.isNotBlank(token)) {
      return Optional.of(token);
    }
    return Optional.empty();
  }

  @Override
  public Optional<String> resolveRefreshTokenFromHeader(HttpServletRequest request) {
    String token = request.getHeader(SecurityConstants.REFRESH_TOKEN_HEADER);
    if (StringUtils.isNotBlank(token)) {
      return Optional.of(token);
    }
    return Optional.empty();
  }
}
