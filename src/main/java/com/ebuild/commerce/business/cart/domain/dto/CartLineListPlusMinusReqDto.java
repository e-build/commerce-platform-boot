package com.ebuild.commerce.business.cart.domain.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CartLineListPlusMinusReqDto {

  @NotEmpty
  private List<CartLineAddParam> cartLineList;

  @Getter
  public static class CartLineAddParam{
    private Long productId;
    private Integer quantity;
  }



}
