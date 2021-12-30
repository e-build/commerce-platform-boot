package com.ebuild.commerce.business.user.seller.domain.dto;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveReqDto;
import com.ebuild.commerce.common.dto.AddressSaveReqDto;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SellerSaveReqDto {

  @Valid
  private CommerceUserSaveReqDto commerceUser;

  @Valid
  private AddressSaveReqDto shippingAddress;

  @NotNull
  private Long companyId;

}
