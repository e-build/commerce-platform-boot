package com.ebuild.commerce.business.user.commerceUserDetail.domain.entity;

import com.ebuild.commerce.business.user.admin.domain.entity.Admin;
import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveReqDto;
import com.ebuild.commerce.business.user.role.domain.CommerceUserRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import com.ebuild.commerce.business.user.seller.domain.entity.Seller;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommerceUserDetail implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String password;

  private String nickname;

  private String phoneNumber;

  @OneToMany(mappedBy = "commerceUserDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<CommerceUserRole> roleList = Lists.newArrayList();

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "commerceUserDetail")
  private Buyer buyer;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "commerceUserDetail")
  private Seller seller;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "commerceUserDetail")
  private Admin admin;

  // security
  @Column
  private Boolean accountNonExpired = true;
  private Boolean accountNonLocked = true;
  private Boolean credentialsNonExpired = true;
  private Boolean enabled = true;

  @Builder
  public CommerceUserDetail(String email, String password, String nickname, String phoneNumber, Role... roles){
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;

    if (ArrayUtils.isNotEmpty(roles)){
      this.roleList = Arrays.stream(roles)
          .map(r -> CommerceUserRole.builder()
              .commerceUserDetail(this)
              .role(r)
              .build())
          .collect(Collectors.toList());
    }
  }

  public void addRole(Role role){
    if (CollectionUtils.isEmpty(this.roleList))
      this.roleList = Lists.newArrayList();
    this.roleList.add(CommerceUserRole.of(this, role));
  }

  public void update(CommerceUserSaveReqDto dto){
    this.nickname = dto.getNickname();
    this.phoneNumber = dto.getPhoneNumber();
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
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

}
