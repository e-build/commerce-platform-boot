package com.ebuild.commerce.business.auth.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class AppRefreshToken {

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  @NotNull
  @Size(max = 64)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "app_user_details_id", unique = true)
  private AppUserDetails appUserDetails;

  @Column(length = 256)
  @NotNull
  @Size(max = 256)
  private String refreshToken;

  public AppRefreshToken(
      @NotNull @Size(max = 64) AppUserDetails appUserDetails
      , @NotNull @Size(max = 256) String refreshToken )
  {
    this.appUserDetails = appUserDetails;
    this.refreshToken = refreshToken;
  }

}
