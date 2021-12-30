package com.ebuild.commerce.business.cart.domain.entity;

import com.ebuild.commerce.business.product.domain.entity.Product;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartLine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Cart cart;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  private int quantity;

  @Builder
  public CartLine(Cart cart, Product product, int quantity) {
    this.cart = cart;
    this.product = product;
    this.quantity = quantity;
  }

  public void minusQuantity(int quantity) {
    if (this.quantity >= quantity)
      throw new IllegalArgumentException("장바구니에 담긴 상품의 수량이 감소시키려는 상품의 수량의 크기보다 작습니다.");
    this.quantity -= quantity;
  }

  public void plusQuantity(int quantity) {
    this.quantity += quantity;
  }
}
