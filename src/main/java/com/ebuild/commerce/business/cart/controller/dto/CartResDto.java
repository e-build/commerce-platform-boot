package com.ebuild.commerce.business.cart.controller.dto;

import com.ebuild.commerce.business.cart.domain.entity.Cart;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartResDto {

  private final Long cartId;
  private final List<CartLineResDto> cartLineList;

  @Builder
  public CartResDto(Cart cart) {
    this.cartId = cart.getId();
    this.cartLineList = cart
        .getCartLineList()
        .stream()
        .map(cartLine -> CartLineResDto
                .builder()
                .cartLine(cartLine)
                .build()
        ).collect(Collectors.toList());
  }
}
