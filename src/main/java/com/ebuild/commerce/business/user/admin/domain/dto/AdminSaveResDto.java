package com.ebuild.commerce.business.user.admin.domain.dto;

import com.ebuild.commerce.business.user.admin.domain.entity.Admin;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveResDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminSaveResDto {

  private final Long adminId;
  private final CommerceUserSaveResDto commerceUser;

  @Builder
  public AdminSaveResDto(Admin admin) {
    this.adminId = admin.getId();
    this.commerceUser = new CommerceUserSaveResDto(admin.getCommerceUserDetail());
  }
}
