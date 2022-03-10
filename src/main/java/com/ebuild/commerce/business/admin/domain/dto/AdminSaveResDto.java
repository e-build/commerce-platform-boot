package com.ebuild.commerce.business.admin.domain.dto;

import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.auth.domain.dto.AppUserSaveResDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminSaveResDto {

  private final Long adminId;
  private final AppUserSaveResDto commerceUser;

  @Builder
  public AdminSaveResDto(Admin admin) {
    this.adminId = admin.getId();
    this.commerceUser = new AppUserSaveResDto(admin.getAppUserDetails());
  }
}
