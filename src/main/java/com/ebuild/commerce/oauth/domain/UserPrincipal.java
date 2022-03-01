package com.ebuild.commerce.oauth.domain;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Setter
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {

    private final String userId;
    private final String email;
    private final String password;
    private final ProviderType providerType;
    private final RoleType roleType;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Builder
    public UserPrincipal(String userId, String email,
        String password, ProviderType providerType,
        RoleType roleType, Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> attributes
    ) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.providerType = providerType;
        this.roleType = roleType;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    public static UserPrincipal create(AppUserDetails appUserDetails) {
        return UserPrincipal.builder()
            .userId(String.valueOf(appUserDetails.getId()))
            .email(appUserDetails.getEmail())
            .password(appUserDetails.getPassword())
            .providerType(appUserDetails.getProviderType())
            .authorities(appUserDetails.getAuthorities())
            .build();
    }

    public static UserPrincipal create(AppUserDetails user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }
}
