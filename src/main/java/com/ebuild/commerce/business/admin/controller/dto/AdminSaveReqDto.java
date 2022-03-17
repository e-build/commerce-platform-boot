package com.ebuild.commerce.business.admin.controller.dto;

import com.ebuild.commerce.business.auth.controller.dto.AppUserDetailsSaveReqDto;
import javax.validation.Valid;
import lombok.Getter;

@Getter
public class AdminSaveReqDto {

  @Valid
  private AppUserDetailsSaveReqDto commerceUser;

}
