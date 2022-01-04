package com.ebuild.commerce.business.order.domain.entity;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.cart.domain.entity.Cart;
import com.ebuild.commerce.business.order.domain.dto.BaseOrderCreateReqDto;
import com.ebuild.commerce.business.orderProduct.domain.common.OrderStatus;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.BaseEntity;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private OrderStatus orderStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id")
  private Buyer buyer;

  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true
      , cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private List<OrderProduct> orderProductList = Lists.newArrayList();

  private LocalDateTime orderDate; //주문시간

  @Embedded
  private Payment payment;

  @Builder
  public Order(Long id, OrderStatus orderStatus
      , Buyer buyer, List<OrderProduct> orderProductList
      , LocalDateTime orderDate) {
    this.id = id;
    this.orderStatus = orderStatus;
    this.buyer = buyer;
    this.orderProductList = orderProductList;
    this.orderDate = orderDate;
  }

  public static Order createCartOrder(Cart cart, BaseOrderCreateReqDto baseOrderCreateReqDto) {
    Order order = new Order();
    order.buyer = cart.getBuyer();
    order.orderStatus = OrderStatus.PAYED;
    order.orderDate = LocalDateTime.now();
    order.payment = Payment.builder()
        .paymentReqDto(baseOrderCreateReqDto.getPayment())
        .build();
    order.orderProductList = cart.getCartLineList()
        .stream()
        .map(cartLine ->
            OrderProduct.builder()
                .order(order)
                .cartLine(cartLine)
                .receivingAddress(
                    baseOrderCreateReqDto.getUseBuyerBasicAddress()
                        ? cart.getBuyer().getReceivingAddress()
                        : new Address(baseOrderCreateReqDto.getReceivingAddress())
                ).build()
        ).collect(Collectors.toList());
    return order;
  }

  public static Order createDirectOrder(
      Buyer buyer,
      List<Product> products,
      BaseOrderCreateReqDto baseOrderCreateReqDto) {
    Order order = new Order();
    order.buyer = buyer;
    order.orderStatus = OrderStatus.PAYED;
    order.orderDate = LocalDateTime.now();
    order.payment = Payment.builder()
        .paymentReqDto(baseOrderCreateReqDto.getPayment())
        .build();
    order.orderProductList = products.stream()
        .map(product ->
            OrderProduct.builder()
                .order(order)
                .product(product)
                .receivingAddress(
                    baseOrderCreateReqDto.getUseBuyerBasicAddress()
                        ? buyer.getReceivingAddress()
                        : new Address(baseOrderCreateReqDto.getReceivingAddress())
                ).build()
        ).collect(Collectors.toList());
    return order;
  }
}
