package com.ebuild.commerce.business.seller.controller.dto;

import com.ebuild.commerce.business.auth.controller.dto.AppUserDetailsSaveReqDto;
import com.ebuild.commerce.common.dto.AddressReqDto;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SellerSaveReqDto {

  @Valid
  private AppUserDetailsSaveReqDto appUserDetails;

  @Valid
  private AddressReqDto shippingAddress;

  @NotNull
  private Long companyId;

}
