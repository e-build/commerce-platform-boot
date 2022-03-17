package com.ebuild.commerce.business.order.controller.dto;

import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.domain.entity.Payment;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderStatus;
import com.ebuild.commerce.business.orderProduct.controller.dto.OrderProductResDto;
import com.ebuild.commerce.common.Address;
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

  private Address receivingAddress;

  private List<OrderProductResDto> orderProductList;

  private Integer totalSaleAmount;

  private Integer totalPayAmount;

  private LocalDateTime orderDate; //주문시간

  private Payment payment;

  @Builder
  public OrderResDto(Long id,
      OrderStatus orderStatus, Address address,
      List<OrderProductResDto> orderProductList,
      LocalDateTime orderDate, Payment payment) {
    this.id = id;
    this.orderStatus = orderStatus;
    this.receivingAddress = address;
    this.orderProductList = orderProductList;
    this.orderDate = orderDate;
    this.payment = payment;
  }

  public static OrderResDto of(Order order){
    return OrderResDto.builder()
        .id(order.getId())
        .orderStatus(order.getOrderStatus())
        .address(order.getBuyer().getReceivingAddress())
        .orderProductList(order.getOrderProductList()
            .stream()
            .map(OrderProductResDto::of)
            .collect(Collectors.toList()))
        .orderDate(order.getOrderDate())
        .payment(order.getPayment())
        .build();
  }

}
