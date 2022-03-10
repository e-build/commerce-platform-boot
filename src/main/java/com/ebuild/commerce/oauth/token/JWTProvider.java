package com.ebuild.commerce.oauth.token;

import com.ebuild.commerce.config.security.properties.AppProperties;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTProvider {

    private final long accessTokenValidTime;
    private final long refreshTokenValidTime;
    private final Key key;

    public JWTProvider(String secret, AppProperties appProperties) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidTime = appProperties.getAuth().getTokenExpiry();
        this.refreshTokenValidTime = appProperties.getAuth().getRefreshTokenExpiry();
    }

    public JWT createAccessToken(String id, List<String> roles){
        return new JWT(id, roles, accessTokenValidTime, key);
    }

    public JWT createRefreshToken(String id, List<String> roles){
        return new JWT(id, roles, refreshTokenValidTime, key);
    }

    public JWT convertAuthToken(String token) {
        return JWT.fromTokenString(token, key);
    }

}



