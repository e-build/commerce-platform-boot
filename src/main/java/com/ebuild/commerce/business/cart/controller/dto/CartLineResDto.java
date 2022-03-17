package com.ebuild.commerce.business.cart.controller.dto;


import com.ebuild.commerce.business.cart.domain.entity.CartLine;
import com.ebuild.commerce.business.product.controller.dto.ProductResDto;
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
    this.product = ProductResDto.of(cartLine.getProduct());
    this.quantity = cartLine.getQuantity();
  }
}
