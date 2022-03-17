package com.ebuild.commerce.business.order.controller.dto;

import com.ebuild.commerce.business.order.controller.dto.OrderQueryParamsDto.OrderListSort;
import com.ebuild.commerce.common.Paging;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderPagingListDto {
  private List<OrderResDto> orders;
  private Paging paging;
  private OrderListSort sort;

  @Builder
  public OrderPagingListDto(
      List<OrderResDto> orders, Paging paging, OrderListSort sort) {
    this.orders = orders;
    this.paging = paging;
    this.sort = sort;
  }
}
