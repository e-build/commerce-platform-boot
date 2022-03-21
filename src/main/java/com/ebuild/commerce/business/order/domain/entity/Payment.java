package com.ebuild.commerce.business.order.domain.entity;

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
  public Payment(PaymentMeans paymentMeans, String cardVendor, Long paymentAmounts,
      LocalDateTime paymentDateTime) {
    this.paymentMeans = paymentMeans;
    this.cardVendor = cardVendor;
    this.paymentAmounts = paymentAmounts;
    this.paymentDateTime = paymentDateTime;
  }

}
