package com.ebuild.commerce.business.auth.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  @Column(length = 64, unique = true)
  @NotNull
  @Size(max = 64)
  private String userId;

  @Column(length = 256)
  @NotNull
  @Size(max = 256)
  private String refreshToken;

  public AppRefreshToken(
      @NotNull @Size(max = 64) String userId
      , @NotNull @Size(max = 256) String refreshToken )
  {
    this.userId = userId;
    this.refreshToken = refreshToken;
  }

}
