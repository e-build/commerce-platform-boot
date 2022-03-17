package com.ebuild.commerce.business.auth.domain.entity;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.auth.controller.dto.AppUserDetailsSaveReqDto;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.oauth.domain.ProviderType;
import com.ebuild.commerce.oauth.info.OAuth2UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserDetails implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<AppUserRole> roleList = Lists.newArrayList();

  public List<String> mapRoleToString(){
    return roleList.stream()
        .map(appUserRole -> appUserRole.getRole().getName().getCode())
        .collect(Collectors.toList());
  }

  @JsonIgnore
  @OneToOne
  private Buyer buyer;

  @JsonIgnore
  @OneToOne
  private Seller seller;

  @JsonIgnore
  @OneToOne
  private Admin admin;

  @Column(unique = true)
  @NotNull
  @Size(max = 512)
  private String email;

  @NotNull
  @Size(max = 1024)
  private String password;

  @Column(unique = true)
  @NotNull
  @Size(max = 20)
  private String nickname;

  @Column(unique = true)
  @NotNull
  @Size(max = 20)
  private String phoneNumber;

  @Setter
  @Column(length = 1)
  @NotNull
  @Size(min = 1, max = 1)
  private String emailVerifiedYn;

  @Column(length = 512)
  @Size(max = 512)
  private String profileImageUrl;

  @Setter
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  @NotNull
  private ProviderType providerType;

  // security
  private Boolean accountNonExpired = true;
  private Boolean accountNonLocked = true;
  private Boolean credentialsNonExpired = true;
  private Boolean enabled = true;

  @Builder
  public AppUserDetails(String email, String password, String nickname, String phoneNumber, ProviderType providerType){
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.providerType = providerType;
  }

  public void addRoles(Role... roles){
    if (isEmpty(this.roleList)) {
      this.roleList = Lists.newArrayList();
    }

    this.roleList.addAll(Arrays.stream(roles)
            .map(r ->
                AppUserRole.builder()
                    .appUserDetails(this)
                    .role(r)
                    .build()
            ).collect(Collectors.toList())
    );
  }

  public void update(AppUserDetailsSaveReqDto dto){
    this.nickname = dto.getNickname();
    this.phoneNumber = dto.getPhoneNumber();
  }

  @Override
  @JsonIgnore
  public String getUsername() {
    return this.email;
  }

  @Override
  @JsonIgnore
  public String getPassword() {
    return this.password;
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roleList
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.getRole().getName().getCode()))
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  public void modifyOauthInfo(OAuth2UserInfo oAuth2UserInfo) {
    if (oAuth2UserInfo.getName() != null && !StringUtils.equals(oAuth2UserInfo.getName(), this.nickname))
      this.nickname = oAuth2UserInfo.getName();

    if (oAuth2UserInfo.getImageUrl() != null && !StringUtils.equals(oAuth2UserInfo.getImageUrl(), this.profileImageUrl))
      this.profileImageUrl = oAuth2UserInfo.getImageUrl();
  }

  public static AppUserDetails newInstanceByOauth(OAuth2UserInfo userInfo, ProviderType providerType){
    AppUserDetails appUserDetails = AppUserDetails.builder()
        .email(userInfo.getEmail())
        .nickname(userInfo.getName())
        .providerType(providerType)
        .password("NO_PASSWORD")
        .phoneNumber("NOT_YET")
        .build();
    appUserDetails.setEmailVerifiedYn("Y");
//    appUserDetails.addRoles();
    return appUserDetails;
  }
}
