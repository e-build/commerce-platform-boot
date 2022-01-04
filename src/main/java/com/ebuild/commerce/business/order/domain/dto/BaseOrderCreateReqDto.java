package com.ebuild.commerce.business.order.domain.dto;

import com.ebuild.commerce.common.dto.AddressReqDto;
import javax.validation.Valid;
import lombok.Getter;

@Getter
public class BaseOrderCreateReqDto {

  private Boolean useBuyerBasicAddress;
  private AddressReqDto receivingAddress;
  @Valid
  private PaymentReqDto payment;
}
