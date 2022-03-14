package com.ebuild.commerce.business.delivery.domain.entity;

import com.ebuild.commerce.business.delivery.domain.common.DeliveryStatus;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_product_id")
  private OrderProduct orderProduct;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride( name = "baseAddress", column = @Column(name = "shipping_base_address"))
      , @AttributeOverride( name = "detailAddress", column = @Column(name = "shipping_detail_address"))
      , @AttributeOverride( name = "addressZipCode", column = @Column(name = "shipping_zip_code"))
  })
  private Address shippingAddress;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride( name = "baseAddress", column = @Column(name = "receiving_base_address"))
      , @AttributeOverride( name = "detailAddress", column = @Column(name = "receiving_detail_address"))
      , @AttributeOverride( name = "addressZipCode", column = @Column(name = "receiving_zip_code"))
  })
  private Address receivingAddress;

  private String trackingNumber;

  private LocalDateTime deliveryStartDateTime;

  private LocalDateTime deliveryEndDateTime;

  @Builder
  public Delivery(
      DeliveryStatus deliveryStatus, OrderProduct orderProduct
      , Address shippingAddress, Address receivingAddress
      , String trackingNumber, LocalDateTime deliveryStartDateTime
      , LocalDateTime deliveryEndDateTime)
  {
    this.deliveryStatus = deliveryStatus;
    this.orderProduct = orderProduct;
    this.shippingAddress = shippingAddress;
    this.receivingAddress = receivingAddress;
    this.trackingNumber = trackingNumber;
    this.deliveryStartDateTime = deliveryStartDateTime;
    this.deliveryEndDateTime = deliveryEndDateTime;
  }
}
