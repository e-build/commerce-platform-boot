package com.ebuild.commerce.business.order.domain.entity;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.cart.domain.entity.Cart;
import com.ebuild.commerce.business.order.controller.dto.BaseOrderCreateReqDto;
import com.ebuild.commerce.business.order.controller.dto.DirectOrderReqDto;
import com.ebuild.commerce.business.order.controller.dto.DirectOrderReqDto.OrderLineDto;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderStatus;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.BaseEntity;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
import lombok.Setter;

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

  @Setter
  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true
      , cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private List<OrderProduct> orderProductList = Lists.newArrayList();

  @Embedded
  private Payment payment;

//  private Long totalNormalAmount;
//
//  private Long totalSaleAmount;

  private LocalDateTime orderDate;


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
    order.payment = baseOrderCreateReqDto.getPayment().toEntity();
    order.orderProductList = cart.getCartLineList()
        .stream()
        .map(cartLine ->
            OrderProduct.of(
                order,
                cartLine.getProduct(),
                findAddress(cart.getBuyer(),baseOrderCreateReqDto),
                cartLine.getQuantity()
            )
        ).collect(Collectors.toList());
    return order;
  }

  public static Order createDirectOrder(
      Buyer buyer,
      List<Product> products,
      DirectOrderReqDto directOrderReqDto) {
    BaseOrderCreateReqDto baseOrderInfo = directOrderReqDto.getBaseOrderInfo();

    Order order = new Order();
    order.buyer = buyer;
    order.orderStatus = OrderStatus.PAYED;
    order.orderDate = LocalDateTime.now();
    order.payment = baseOrderInfo.getPayment().toEntity();
    order.orderProductList = products.stream()
        .map(product ->
            OrderProduct.of(
                order,
                product,
                findAddress(buyer, baseOrderInfo),
                findOrderProductQuantity(directOrderReqDto, product)
            )
        )
        .collect(Collectors.toList());
    return order;
  }

  private static Address findAddress(Buyer buyer, BaseOrderCreateReqDto baseOrderInfo) {
    return baseOrderInfo.getUseBuyerBasicAddress()
        ? buyer.getReceivingAddress()
        : baseOrderInfo.getReceivingAddress().toEntity();
  }

  private static Long findOrderProductQuantity(DirectOrderReqDto directOrderReqDto, Product product) {
    Long quantity = 0L;
    for ( OrderLineDto orderLine : directOrderReqDto.getOrderLineList() ){
      if (Objects.equals(orderLine.getProductId(), product.getId()))
        quantity = orderLine.getQuantity();
    }
    return quantity;
  }

}
