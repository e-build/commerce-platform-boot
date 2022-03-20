package com.ebuild.commerce.business.order.controller.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DirectOrderReqDto {

  @Valid
  private List<OrderLineDto> orderLineList;

  @Valid
  private BaseOrderCreateReqDto baseOrderInfo;

  @Getter
  public static class OrderLineDto {
    @NotNull
    private Long productId;
    @NotNull
    private Long quantity;
  }

}
