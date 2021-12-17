package com.ebuild.commerce.business.order.domain;

import com.ebuild.commerce.business.delivery.domain.Delivery;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.business.orderProduct.domain.common.OrderStatus;
import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import com.ebuild.commerce.common.DateTimeAuditing;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends DateTimeAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private OrderStatus orderStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id")
  private Buyer buyer;

  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<OrderProduct> orderProductList;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id")
  private Delivery devliery;

  private LocalDateTime orderDate; //주문시간

}
