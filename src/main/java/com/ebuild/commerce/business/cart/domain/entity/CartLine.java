package com.ebuild.commerce.business.cart.domain.entity;

import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartLine extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Cart cart;

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  private Long quantity;

  @Builder
  public CartLine(Cart cart, Product product, Long quantity) {
    this.cart = cart;
    this.product = product;
    this.quantity = quantity;
  }

  public void minusQuantity(Long quantity) {
    if (this.quantity < quantity)
      throw new IllegalArgumentException("장바구니에 담긴 상품의 수량이 감소시키려는 상품의 수량의 크기보다 작습니다.");
    this.quantity -= quantity;
  }

  public void plusQuantity(Long quantity) {
    this.quantity += quantity;
  }
}
