package com.ebuild.commerce.business.order.domain.entity;

import com.ebuild.commerce.business.order.controller.dto.PaymentReqDto;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

  @Enumerated(value = EnumType.STRING)
  private PaymentMeans paymentMeans;
  private String cardVendor;
  private Long paymentAmounts;
  private LocalDateTime paymentDateTime;

  @Builder
  public Payment(PaymentReqDto paymentReqDto) {
    this.paymentMeans = PaymentMeans.fromValue(paymentReqDto.getPaymentMeans());
    this.cardVendor = paymentReqDto.getCardVendor();
    this.paymentAmounts = paymentReqDto.getPaymentAmounts();
    this.paymentDateTime = paymentReqDto.getPaymentDateTime();
  }
}
