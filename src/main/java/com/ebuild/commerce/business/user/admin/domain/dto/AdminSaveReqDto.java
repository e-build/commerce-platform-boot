package com.ebuild.commerce.business.user.admin.domain.dto;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveReqDto;
import javax.validation.Valid;
import lombok.Getter;

@Getter
public class AdminSaveReqDto {

  @Valid
  private CommerceUserSaveReqDto commerceUser;

}
