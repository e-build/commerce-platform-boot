package com.ebuild.commerce.business.order.domain.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DirectOrderReqDto {

  @Valid
  private List<OrderLineListDto> orderLineList;

  @Valid
  private BaseOrderCreateReqDto baseOrderInfo;

  @Getter
  public static class OrderLineListDto {

    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
  }

}
