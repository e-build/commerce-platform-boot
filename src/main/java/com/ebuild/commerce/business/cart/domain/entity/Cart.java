package com.ebuild.commerce.business.cart.domain.entity;

import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.user.buyer.domain.Buyer;
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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="buyer_id", nullable = true)
  private Buyer buyer;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CartLine> cartLineList = Lists.newArrayList();

  public static Cart newInstance(){
    return new Cart();
  }

  public void addProduct(Product product, int quantity){
    // 장바구니에 해당 상품이 존재하면 수량 증가
    if (this.cartLineList.stream().anyMatch(cartLine -> cartLine.getProduct().getId() == product.getId())){
      for (CartLine cartLine : this.cartLineList) {
        if (Objects.equals(cartLine.getProduct().getId(), product.getId())){
          cartLine.plusQuantity(quantity);
          break;
        }
      }
    }

    // 해당 상품이 존재하면 수량 증가
    this.cartLineList.add(
        CartLine.builder()
            .cart(this)
            .product(product)
            .quantity(quantity)
        .build()
    );
  }

  public void removeProduct(Product product, int quantity){
    for (CartLine cartLine : this.cartLineList) {
      if (Objects.equals(cartLine.getProduct().getId(), product.getId())){
          cartLine.minusQuantity(quantity);
          break;
      }
    }
    throw new NotFoundException("장바구니에 해당 상품이 존재하지 않습니다. productId : ["+product.getId()+"]");
  }

  public void clear(){
    this.cartLineList.clear();
  }



}
