package com.ebuild.commerce.business.seller.domain.dto;

import com.ebuild.commerce.business.auth.domain.dto.AppUserSaveReqDto;
import com.ebuild.commerce.common.dto.AddressReqDto;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SellerSaveReqDto {

  @Valid
  private AppUserSaveReqDto commerceUser;

  @Valid
  private AddressReqDto shippingAddress;

  @NotNull
  private Long companyId;

}
