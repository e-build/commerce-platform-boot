package com.ebuild.commerce.config.security.filter;

import com.ebuild.commerce.business.auth.domain.AppUserDetailSecurityContextAdapter;
import com.ebuild.commerce.business.auth.service.AppUserDetailsQueryService;
import com.ebuild.commerce.common.RedisService;
import com.ebuild.commerce.config.security.SecurityConstants;
import com.ebuild.commerce.oauth.token.JWT;
import com.ebuild.commerce.oauth.token.JWTProvider;
import com.ebuild.commerce.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final RedisService redisService;
    private final AppUserDetailsQueryService appUserDetailsQueryService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String tokenStr = HeaderUtil.getAccessToken(request);
        JWT jwt = jwtProvider.convertAuthToken(tokenStr);

        if (jwt.validate()) {
            Authentication authentication = getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(JWT jwt) {
        Claims claims = jwt.resolveTokenClaims();
        String email = claims.getSubject();
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(new String[]{claims.get(SecurityConstants.JWT_AUTHORITIES_KEY).toString()})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        log.debug("claims subject := [{}]", email);
        AppUserDetailSecurityContextAdapter principal = new AppUserDetailSecurityContextAdapter(redisService.getUser(email));
        return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
    }

}
