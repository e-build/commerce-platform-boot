package com.ebuild.commerce.oauth.token;

import com.ebuild.commerce.config.security.properties.AppProperties;
import com.ebuild.commerce.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Slf4j
public class JWTProvider {

    private final long accessTokenValidTime;
    private final long refreshTokenValidTime;
    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public JWTProvider(String secret, AppProperties appProperties) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidTime = appProperties.getAuth().getTokenExpiry();
        this.refreshTokenValidTime = appProperties.getAuth().getRefreshTokenExpiry();
    }

    public JWT createAccessToken(String uniqueId, List<String> roles){
        return new JWT(uniqueId, roles, accessTokenValidTime, key);
    }

    public JWT createRefreshToken(String uniqueId, List<String> roles){
        return new JWT(uniqueId, roles, refreshTokenValidTime, key);
    }

    public JWT convertAuthToken(String token) {
        return JWT.fromToken(token, key);
    }

    public Authentication getAuthentication(JWT authToken) {
        if (!authToken.validate()) {
            throw new TokenValidFailedException();
        }

        Claims claims = authToken.resolveTokenClaims();
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        log.debug("claims subject := [{}]", claims.getSubject());
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
    }

}



