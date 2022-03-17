package com.ebuild.commerce.business.order.domain.dto;

import com.ebuild.commerce.business.order.domain.entity.PaymentMeans;
import com.ebuild.commerce.common.validation.Enum;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentReqDto {

  @Enum(enumClass = PaymentMeans.class, message = "CREDIT_CARD, CASH 중 하나의 값이 입력되어야 합니다.")
  private String paymentMeans;
  private String cardVendor;
  @NotNull
  private Integer paymentAmounts;
  @NotNull
  private LocalDateTime paymentDateTime;

}
