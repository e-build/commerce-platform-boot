package com.ebuild.commerce.business.admin.domain.dto;

import com.ebuild.commerce.business.auth.domain.dto.AppUserDetailsSaveReqDto;
import javax.validation.Valid;
import lombok.Getter;

@Getter
public class AdminSaveReqDto {

  @Valid
  private AppUserDetailsSaveReqDto commerceUser;

}
