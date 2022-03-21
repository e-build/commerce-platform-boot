package com.ebuild.commerce.business.order.controller.dto;

import com.ebuild.commerce.business.order.domain.entity.Payment;
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
  private Long paymentAmounts;
  @NotNull
  private LocalDateTime paymentDateTime;

  public Payment toEntity() {
    return Payment.builder()
        .paymentMeans(PaymentMeans.fromValue(getPaymentMeans()))
        .cardVendor(getCardVendor())
        .paymentAmounts(getPaymentAmounts())
         .paymentDateTime(getPaymentDateTime())
        .build();
  }

}
