package com.ebuild.commerce.business.orderProduct.domain.entity;

import com.ebuild.commerce.business.delivery.domain.entity.DeliveryStatus;
import com.ebuild.commerce.business.delivery.domain.entity.Delivery;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  @OneToOne(fetch = FetchType.LAZY
      , cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private Long normalAmount;

  private Long saleAmount;

  private Long quantity;

  @Builder
  public OrderProduct(Long id, Order order,
      Product product, Delivery delivery, Long normalAmount, Long saleAmount,
      Long quantity) {
    this.id = id;
    this.order = order;
    this.product = product;
    this.delivery = delivery;
    this.normalAmount = normalAmount;
    this.saleAmount = saleAmount;
    this.quantity = quantity;
  }

  public static OrderProduct of(Order order, Product product, Address receivingAddress, Long quantity){
    OrderProduct orderProduct = OrderProduct.builder()
        .product(product)
        .order(order)
        .normalAmount(product.getNormalAmount())
        .saleAmount(product.getSaleAmount())
        .quantity(quantity)
        .build();
    orderProduct.initDelivery(DeliveryStatus.READY, product.getCompany().getAddress(), receivingAddress);
    return orderProduct;
  }

  private void initDelivery(DeliveryStatus deliveryStatus, Address sendingAddress, Address receivingAddress) {
    this.delivery = Delivery.builder()
        .orderProduct(this)
        .deliveryStatus(deliveryStatus)
        .receivingAddress(receivingAddress)
        .shippingAddress(sendingAddress)
        .build();
  }
}
