package com.ebuild.commerce.business.cart.domain.dto;


import com.ebuild.commerce.business.cart.domain.entity.CartLine;
import com.ebuild.commerce.business.product.domain.dto.ProductResDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartLineResDto {

  private final Long cartLineId;
  private final ProductResDto product;
  private final Integer quantity;

  @Builder
  public CartLineResDto(CartLine cartLine) {
    this.cartLineId = cartLine.getId();
    this.product = ProductResDto.builder()
        .product(cartLine.getProduct())
        .build();
    this.quantity = cartLine.getQuantity();
  }
}
