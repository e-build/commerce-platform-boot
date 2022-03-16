package com.ebuild.commerce.business.delivery.domain.dto;

import com.ebuild.commerce.business.delivery.domain.common.DeliveryStatus;
import com.ebuild.commerce.business.delivery.domain.entity.Delivery;
import com.ebuild.commerce.common.dto.AddressSaveResDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryResDto {

  private Long id;
  private Long orderProductId;
  private DeliveryStatus deliveryStatus;
  private AddressSaveResDto shippingAddress;
  private AddressSaveResDto receivingAddress;
  private String trackingNumber;
  private LocalDateTime deliveryStartDateTime;
  private LocalDateTime deliveryEndDateTime;

  @Builder
  public DeliveryResDto(Long id, Long orderProductId,
      DeliveryStatus deliveryStatus, AddressSaveResDto shippingAddress,
      AddressSaveResDto receivingAddress, String trackingNumber,
      LocalDateTime deliveryStartDateTime, LocalDateTime deliveryEndDateTime) {
    this.id = id;
    this.orderProductId = orderProductId;
    this.deliveryStatus = deliveryStatus;
    this.shippingAddress = shippingAddress;
    this.receivingAddress = receivingAddress;
    this.trackingNumber = trackingNumber;
    this.deliveryStartDateTime = deliveryStartDateTime;
    this.deliveryEndDateTime = deliveryEndDateTime;
  }

  public static DeliveryResDto of(Delivery entity){
    return DeliveryResDto.builder()
        .build();
  }

}
