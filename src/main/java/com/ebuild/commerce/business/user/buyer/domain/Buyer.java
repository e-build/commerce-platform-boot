package com.ebuild.commerce.business.user.buyer.domain;

import com.ebuild.commerce.business.cart.domain.entity.Cart;
import com.ebuild.commerce.business.order.domain.Order;
import com.ebuild.commerce.common.Address;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Buyer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private String nickName;
  private String phoneNumber;

  @Embedded
  private Address address;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Cart cart;

  @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
  private List<Order> ordersList;

}
