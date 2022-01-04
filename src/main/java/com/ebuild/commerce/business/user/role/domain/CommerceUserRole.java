package com.ebuild.commerce.business.user.role.domain;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
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
public class CommerceUserRole {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="role_id")
  private Role role;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="commerce_user_detail_id")
  private CommerceUserDetail commerceUserDetail;

  public static CommerceUserRole of(CommerceUserDetail commerceUserDetail, Role role) {
    return CommerceUserRole.builder()
        .commerceUserDetail(commerceUserDetail)
        .role(role)
        .build();
  }
}
