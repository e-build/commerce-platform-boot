package com.ebuild.commerce.business.user.commerceUser;

import com.ebuild.commerce.business.user.admin.domain.Admin;
import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import com.ebuild.commerce.business.user.role.domain.CommerceUserRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import com.ebuild.commerce.business.user.seller.domain.Seller;
import com.google.common.collect.Lists;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

  private String nickName;

  private String phoneNumber;

  @OneToMany(mappedBy = "commerceUserDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<CommerceUserRole> roleList = Lists.newArrayList();

  @OneToOne(fetch = FetchType.LAZY)
  private Buyer buyer;

  @OneToOne(fetch = FetchType.LAZY)
  private Seller seller;

  @OneToOne(fetch = FetchType.LAZY)
  private Admin admin;

  // security
  @Column
  private Boolean accountNonExpired = true;
  private Boolean accountNonLocked = true;
  private Boolean credentialsNonExpired = true;
  private Boolean enabled = true;

  public CommerceUserDetail(String email, String password){
    this.email = email;
    this.password = password;
  }

  public void addRole(Role role){
    if (CollectionUtils.isEmpty(this.roleList))
      this.roleList = Lists.newArrayList();
    this.roleList.add(CommerceUserRole.of(this, role));
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
