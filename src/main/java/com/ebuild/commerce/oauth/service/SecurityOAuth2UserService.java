package com.ebuild.commerce.oauth.service;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.oauth.domain.ProviderType;
import com.ebuild.commerce.oauth.domain.UserPrincipal;
import com.ebuild.commerce.oauth.exception.OAuthProviderMissMatchException;
import com.ebuild.commerce.oauth.info.OAuth2UserInfo;
import com.ebuild.commerce.oauth.info.OAuth2UserInfoFactory;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityOAuth2UserService extends DefaultOAuth2UserService {

    private final JpaBuyerRepository jpaBuyerRepository;
    private final JpaRoleRepository jpaRoleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        AppUserDetails savedUser = jpaBuyerRepository
            .findByEmail(userInfo.getEmail())
            .orElseGet(() -> createUser(userInfo, providerType))
            .getAppUserDetails();

        if (providerType != savedUser.getProviderType()) {
            throw new OAuthProviderMissMatchException(
                "Looks like you're signed up with " + providerType
                    + " account. Please use your " + savedUser.getProviderType()
                    + " account to login."
            );
        }
        updateUser(savedUser, userInfo);

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private Buyer createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        AppUserDetails appUserDetails = AppUserDetails.newInstanceByOauth(userInfo, providerType);
        appUserDetails.addRoles(jpaRoleRepository.findAllByNameIn(Lists.newArrayList(RoleType.BUYER)).toArray(new Role[0]));
        appUserDetails.setEmailVerifiedYn("Y");

        return jpaBuyerRepository.saveAndFlush(Buyer.builder()
            .appUserDetails(appUserDetails)
            .build());
    }

    private void updateUser(AppUserDetails appUserDetails, OAuth2UserInfo userInfo) {
        appUserDetails.modifyOauthInfo(userInfo);
    }

}
