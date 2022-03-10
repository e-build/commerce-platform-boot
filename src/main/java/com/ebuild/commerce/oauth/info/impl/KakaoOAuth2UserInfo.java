package com.ebuild.commerce.oauth.info.impl;

import com.ebuild.commerce.oauth.info.OAuth2UserInfo;
import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private boolean profileNicknameNeedsAgreement;
    private boolean profileImageNeedsAgreement;
    private boolean emailNeedsAgreement;
    private String email;
    private String nickname;
    private String thumbnailImageUrl;
    private String profileImageUrl;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        resolveOauthUserAttributes(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return this.nickname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getImageUrl() {
        return this.thumbnailImageUrl;
    }

    private void resolveOauthUserAttributes(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.profileNicknameNeedsAgreement = (boolean)kakaoAccount.get("profile_nickname_needs_agreement");
        this.profileImageNeedsAgreement = (boolean)kakaoAccount.get("profile_image_needs_agreement");
        this.emailNeedsAgreement = (boolean)kakaoAccount.get("email_needs_agreement");
        this.email = (String)kakaoAccount.get("email");

        Map<String, Object> profile = (Map<String, Object>)kakaoAccount.get("profile");
        this.nickname = (String)profile.get("nickname");
        this.thumbnailImageUrl = (String)profile.get("thumbnail_image_url");
        this.profileImageUrl = (String)profile.get("profile_image_url");
    }
}
