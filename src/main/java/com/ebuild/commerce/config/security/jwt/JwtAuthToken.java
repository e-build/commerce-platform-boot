package com.ebuild.commerce.config.security.jwt;

import com.ebuild.commerce.config.security.SecurityConstants;
import com.ebuild.commerce.exception.security.JwtTokenCreationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthToken implements AuthToken<Claims>{

  @Getter
  private final String token;
  private final String secretKey;

  JwtAuthToken(String token, String secretKey) {
    this.token = token;
    this.secretKey = secretKey;
  }

  JwtAuthToken(String userId, String role, Date expiredDate, String secretKey) {
    this.secretKey = secretKey;
    this.token = createJwtAuthToken(userId, role, expiredDate)
        .orElseThrow(JwtTokenCreationException::new);
  }

  @Override
  public boolean validate() {
    return getData() != null;
  }

  @Override
  public Claims getData() {
    try {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (SecurityException e) {
      log.info("Invalid JWT signature.");
      throw e;
    } catch (MalformedJwtException e) {
      log.info("Invalid JWT token.");
      throw e;
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token.");
      throw e;
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
      throw e;
    } catch (IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
      throw e;
    }
  }

  private Optional<String> createJwtAuthToken(String userId, String role, Date expiredDate) {
    return Optional.ofNullable(
        Jwts.builder()
        .setSubject(userId)
        .claim(SecurityConstants.AUTHORITIES_KEY, role)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .setExpiration(expiredDate)
        .compact()
    );
  }

}
