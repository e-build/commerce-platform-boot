package com.ebuild.commerce.business.auth.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUserRole {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="role_id")
  private Role role;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="commerce_user_detail_id")
  private AppUserDetails appUserDetails;

  public static AppUserRole of(AppUserDetails appUserDetails, Role role) {
    return AppUserRole.builder()
        .appUserDetails(appUserDetails)
        .role(role)
        .build();
  }
}
