package com.ebuild.commerce.business.order.controller.dto;

import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.domain.entity.Payment;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderStatus;
import com.ebuild.commerce.business.orderProduct.controller.dto.OrderProductResDto;
import com.ebuild.commerce.common.Address;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResDto {

  private Long id;

  private OrderStatus orderStatus;

  private List<OrderProductResDto> orderProductList;

  private Long totalSaleAmount;

  private Long totalPayAmount;

  private LocalDateTime orderDate; //주문시간

  private Payment payment;

  @QueryProjection
  public OrderResDto(Long id, OrderStatus orderStatus,
      Long totalSaleAmount, Long totalPayAmount,
      LocalDateTime orderDate, Payment payment) {
    this.id = id;
    this.orderStatus = orderStatus;
    this.totalSaleAmount = totalSaleAmount;
    this.totalPayAmount = totalPayAmount;
    this.orderDate = orderDate;
    this.payment = payment;
  }

  @Builder
  public OrderResDto(Long id, OrderStatus orderStatus,
      List<OrderProductResDto> orderProductList,
      Long totalSaleAmount, Long totalPayAmount,
      LocalDateTime orderDate, Payment payment) {
    this.id = id;
    this.orderStatus = orderStatus;
    this.orderProductList = orderProductList;
    this.totalSaleAmount = totalSaleAmount;
    this.totalPayAmount = totalPayAmount;
    this.orderDate = orderDate;
    this.payment = payment;
  }

  public static OrderResDto of(Order order){
    return OrderResDto.builder()
        .id(order.getId())
        .orderStatus(order.getOrderStatus())
//        .address(order.getBuyer().getReceivingAddress())
        .orderProductList(
            order.getOrderProductList()
            .stream()
            .map(OrderProductResDto::of)
            .collect(Collectors.toList()))
        .orderDate(order.getOrderDate())
        .payment(order.getPayment())
        .build();
  }

}
