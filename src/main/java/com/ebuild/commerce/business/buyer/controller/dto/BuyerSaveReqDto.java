package com.ebuild.commerce.business.buyer.controller.dto;

import com.ebuild.commerce.business.auth.controller.dto.AppUserDetailsSaveReqDto;
import com.ebuild.commerce.common.dto.AddressReqDto;
import javax.validation.Valid;
import lombok.Data;
import lombok.Getter;

@Data
public class BuyerSaveReqDto {

  private Long id;

  @Valid
  private AppUserDetailsSaveReqDto appUserDetails;

  @Valid
  private AddressReqDto receiveAddress;

}
