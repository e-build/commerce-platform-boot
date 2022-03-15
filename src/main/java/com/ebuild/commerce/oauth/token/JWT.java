package com.ebuild.commerce.oauth.token;

import static java.util.Objects.*;

import com.ebuild.commerce.config.security.SecurityConstants;
import com.ebuild.commerce.exception.security.JwtTokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Slf4j
public class JWT {

    @Getter
    private final String token;
    private final Key key;

    private JWT (String token, Key key){
        this.token = token;
        this.key = key;
    }

    JWT(String id, List<String> roleList, long expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, join(roleList), resolveExpiredDateFromNow(expiry));
    }

    public static JWT fromTokenString(String token, Key key){
        return new JWT(token, key);
    }

    public boolean validate() {
        return !isNull(this.resolveTokenClaims());
    }

    public Claims resolveTokenClaims() {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (SecurityException e) {
            log.error("Invalid JWT signature.");
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.");
            throw e;
        }
    }

    public String resolveEmail(){
        return resolveTokenClaims().getSubject();
    }

    public String resolveOAuthLoginSuccessTokenEmail(){
        return resolveTokenClaims().getSubject().substring(SecurityConstants.OAUTH_LOGIN_SUCCESS_PREFIX.length());
    }

    public List<? extends GrantedAuthority> resolveRoleList(){
        return Arrays.stream(
            String.valueOf(
                resolveTokenClaims().get(SecurityConstants.JWT_AUTHORITIES_KEY)).split(",")
            )
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()
        );
    }

    public List<String> resolveRoleStringList(){
        return Arrays.stream(
                String.valueOf(
                    resolveTokenClaims().get(SecurityConstants.JWT_AUTHORITIES_KEY)).split(",")
            )
            .map(String::new)
            .collect(Collectors.toList()
            );
    }

    private String createAuthToken(String id, String roles, Date expiredDate) {
        return Jwts.builder()
            .setSubject(id)
            .claim(SecurityConstants.JWT_AUTHORITIES_KEY, roles)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(expiredDate)
            .compact();
    }

    private Date resolveExpiredDateFromNow(long expirySeconds){
        return new Date(new Date().getTime() + expirySeconds);
    }

    private String join(List<String> roleList) {
        return StringUtils.join(roleList, ",");
    }
}
