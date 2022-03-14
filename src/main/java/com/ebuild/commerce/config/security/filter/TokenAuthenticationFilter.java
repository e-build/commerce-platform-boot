package com.ebuild.commerce.config.security.filter;

import com.ebuild.commerce.business.auth.domain.AuthenticationAdapter;
import com.ebuild.commerce.business.auth.domain.UserSubject;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.exception.security.JwtTokenExpiredException;
import com.ebuild.commerce.exception.security.JwtTokenInvalidException;
import com.ebuild.commerce.oauth.token.JWT;
import com.ebuild.commerce.oauth.token.JWTProvider;
import com.ebuild.commerce.util.HeaderUtil;
import com.ebuild.commerce.util.HttpUtils;
import com.google.common.collect.Maps;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final JsonHelper jsonHelper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String tokenStr = HeaderUtil.getAccessToken(request);
        JWT jwt = jwtProvider.convertAuthToken(tokenStr);

        try{
            if (jwt.validate())
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(jwt));
        } catch (ExpiredJwtException e){
            HttpUtils.jsonFlush(
                response,
                HttpServletResponse.SC_UNAUTHORIZED,
                jsonHelper.serialize(CommonResponse.ERROR(new JwtTokenExpiredException()))
            );
            return;
        }
        filterChain.doFilter(request, response);

    }

    private Authentication getAuthentication(JWT jwt) {
        return new UsernamePasswordAuthenticationToken(
            new AuthenticationAdapter(UserSubject.of(jwt.resolveEmail())),
            jwt,
            jwt.resolveRoleList()
        );
    }

}
