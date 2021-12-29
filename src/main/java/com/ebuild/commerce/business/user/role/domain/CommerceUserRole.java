package com.ebuild.commerce.business.user.role.domain;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="role_id")
  private Role role;

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
