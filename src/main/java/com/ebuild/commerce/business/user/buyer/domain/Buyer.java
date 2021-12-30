package com.ebuild.commerce.business.user.buyer.domain;

import com.ebuild.commerce.business.cart.domain.entity.Cart;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.BaseEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Buyer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "commerce_user_detail_id")
  private CommerceUserDetail commerceUserDetail;

  @Embedded
  private Address receivingAddress;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name="cart_id", nullable = false)
  private Cart cart;

  @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
  private List<Order> ordersList = Lists.newArrayList();

  @Builder
  public Buyer(CommerceUserDetail commerceUserDetail, Address receivingAddress) {
    this.commerceUserDetail = commerceUserDetail;
    this.receivingAddress = receivingAddress;
    this.cart = Cart.newInstance();
  }

}
