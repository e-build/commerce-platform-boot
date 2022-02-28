package com.ebuild.commerce.oauth.token;

import com.ebuild.commerce.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Slf4j
public class JWTProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public JWTProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public JWT createAuthToken(String id, Date expiry) {
        return new JWT(id, expiry, key);
    }

    public JWT createAuthToken(String id, String role, Date expiry) {
        return new JWT(id, role, expiry, key);
    }

    public JWT convertAuthToken(String token) {
        return new JWT(token, key);
    }

    public Authentication getAuthentication(JWT authToken) {
        if (!authToken.validate()) {
            throw new TokenValidFailedException();
        }

        Claims claims = authToken.getTokenClaims();
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        log.debug("claims subject := [{}]", claims.getSubject());
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
    }

}



