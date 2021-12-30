package com.ebuild.commerce.business.user.buyer.domain.dto;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveReqDto;
import com.ebuild.commerce.common.dto.AddressSaveReqDto;
import javax.validation.Valid;
import lombok.Getter;

@Getter
public class BuyerSaveReqDto {

  private Long id;

  @Valid
  private CommerceUserSaveReqDto commerceUser;

  @Valid
  private AddressSaveReqDto receiveAddress;

}
