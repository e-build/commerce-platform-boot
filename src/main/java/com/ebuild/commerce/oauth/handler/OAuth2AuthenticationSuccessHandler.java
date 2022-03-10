package com.ebuild.commerce.oauth.handler;

import static com.ebuild.commerce.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.*;

import com.ebuild.commerce.business.auth.domain.entity.AppRefreshToken;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaRefreshTokenRepository;
import com.ebuild.commerce.config.security.properties.AppProperties;
import com.ebuild.commerce.oauth.domain.ProviderType;
import com.ebuild.commerce.oauth.info.OAuth2UserInfo;
import com.ebuild.commerce.oauth.info.OAuth2UserInfoFactory;
import com.ebuild.commerce.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.ebuild.commerce.oauth.token.JWT;
import com.ebuild.commerce.oauth.token.JWTProvider;
import com.ebuild.commerce.util.CookieUtil;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTProvider tokenProvider;
    private final AppProperties appProperties;
    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser user = ((OidcUser) authentication.getPrincipal());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Collection<? extends GrantedAuthority> authorities = ((OidcUser) authentication.getPrincipal()).getAuthorities();

        ArrayList<String> roles = Lists.newArrayList(RoleType.BUYER.getCode());
        JWT accessToken = tokenProvider.createAccessToken(userInfo.getEmail(), roles);
        JWT refreshToken = tokenProvider.createRefreshToken(userInfo.getEmail(), roles);

        // DB 저장
        AppRefreshToken userRefreshToken = jpaRefreshTokenRepository
            .findByUserId(userInfo.getId())
            .orElseGet(()->
                jpaRefreshTokenRepository.saveAndFlush(new AppRefreshToken(userInfo.getId(), refreshToken.getToken()))
            );
        userRefreshToken.setRefreshToken(refreshToken.getToken());

        int cookieMaxAge = (int)appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken.getToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2()
            .getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                        && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }
}
