package com.ebuild.commerce.oauth.token;

import com.ebuild.commerce.config.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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

    private String join(List<String> roleList) {
        return StringUtils.join(roleList, ",");
    }

    public static JWT fromTokenString(String token, Key key){
        return new JWT(token, key);
    }

    private String createAuthToken(String id, String roles, Date expiredDate) {
        return Jwts.builder()
                .setSubject(id)
                .claim(SecurityConstants.JWT_AUTHORITIES_KEY, roles)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredDate)
                .compact();
    }

    public boolean validate() {
        return this.resolveTokenClaims() != null;
    }

    public Claims resolveTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    private Date resolveExpiredDateFromNow(long expirySeconds){
        return new Date(new Date().getTime() + expirySeconds);
    }
}
