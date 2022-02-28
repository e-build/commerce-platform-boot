package com.ebuild.commerce.business.buyer.domain.dto;

import com.ebuild.commerce.business.auth.domain.dto.AppUserSaveReqDto;
import com.ebuild.commerce.common.dto.AddressReqDto;
import javax.validation.Valid;
import lombok.Getter;

@Getter
public class BuyerSaveReqDto {

  private Long id;

  @Valid
  private AppUserSaveReqDto commerceUser;

  @Valid
  private AddressReqDto receiveAddress;

}
