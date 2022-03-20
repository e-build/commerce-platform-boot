package com.ebuild.commerce.business.cart.domain.entity;

import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.common.BaseEntity;
import com.ebuild.commerce.exception.NotFoundException;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "cart")
  @JoinColumn(name="buyer_id", nullable = true)
  private Buyer buyer;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartLine> cartLineList = Lists.newArrayList();

  public static Cart newInstance(){
    return new Cart();
  }

  public void addProduct(Product product, Long quantity){
    // 장바구니에 해당 상품이 존재하면 수량 증가
    boolean isProductExists = this.cartLineList
        .stream()
        .anyMatch(cartLine ->
            Objects.equals(cartLine.getProduct().getId(), product.getId())
        );
    if (isProductExists){
      for (CartLine cartLine : this.cartLineList) {
        if (Objects.equals(cartLine.getProduct().getId(), product.getId())){
          cartLine.plusQuantity(quantity);
          return;
        }
      }
    }

    // 장바구니에 해당 상품이 존재하지 않으면 상품 추가
    this.cartLineList.add(
        CartLine.builder()
            .cart(this)
            .product(product)
            .quantity(quantity)
        .build()
    );
  }

  public void removeProduct(Product product, Long quantity){
    for (CartLine cartLine : this.cartLineList) {
      if (Objects.equals(cartLine.getProduct().getId(), product.getId())){
          cartLine.minusQuantity(quantity);
          if (cartLine.getQuantity() == 0)
            this.cartLineList.remove(cartLine);
          return;
      }
    }
    throw new NotFoundException(String.valueOf(product.getId()), "장바구니 상품");
  }

  public void clear(){
    this.cartLineList.clear();
  }



}
