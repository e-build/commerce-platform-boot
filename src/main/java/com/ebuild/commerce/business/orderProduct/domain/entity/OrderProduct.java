package com.ebuild.commerce.business.orderProduct.domain.entity;

import com.ebuild.commerce.business.cart.domain.entity.CartLine;
import com.ebuild.commerce.business.delivery.domain.common.DeliveryStatus;
import com.ebuild.commerce.business.delivery.domain.entity.Delivery;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.BaseEntity;
import java.util.Objects;
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
import lombok.AllArgsConstructor;
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

  private Integer unitAmount;

  private Integer quantity;

  @Builder
  public OrderProduct(Order order, CartLine cartLine, Product product, Address receivingAddress) {
    this.product = !Objects.isNull(product) ? product : cartLine.getProduct();
    this.order = order;
    this.delivery = Delivery.builder()
        .orderProduct(this)
        .deliveryStatus(DeliveryStatus.READY)
        .receivingAddress(receivingAddress)
        .shippingAddress(this.product.getCompany().getAddress())
        .build();
    this.unitAmount = this.product.getSaleAmount();
    this.quantity = cartLine.getQuantity();
  }
}
