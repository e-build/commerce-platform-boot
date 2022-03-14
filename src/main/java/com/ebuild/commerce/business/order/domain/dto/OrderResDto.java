package com.ebuild.commerce.business.order.domain.dto;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.delivery.domain.entity.Delivery;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.domain.entity.Payment;
import com.ebuild.commerce.business.orderProduct.domain.common.OrderStatus;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.dto.AddressSaveResDto;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResDto {

  private Long id;

  private OrderStatus orderStatus;

  private Address receivingAddress;

  private List<OrderProduct> orderProductList = Lists.newArrayList();
//  private String productName;
//  private String normalAmount;
//  private String saleAmount;
//  private Delivery delivery;

  private LocalDateTime orderDate; //주문시간

  private Payment payment;

  @Builder
  public OrderResDto(Long id,
      OrderStatus orderStatus, Address address,
      List<OrderProduct> orderProductList, LocalDateTime orderDate,
      Payment payment) {
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
        .orderProductList(order.getOrderProductList())
        .orderDate(order.getOrderDate())
        .payment(order.getPayment())
        .build();
  }


}
