package com.ebuild.commerce.business.order.controller.dto;

import static java.util.Objects.isNull;

import com.ebuild.commerce.common.Paging;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderQueryParamsDto {

  private String productName;
  private List<String> category;
  private OrderListSort sort = new OrderListSort();
  private Paging paging;

  @Getter
  @Setter
  @NoArgsConstructor
  public static class OrderListSort {
    private String payAmount;
    private String orderDate = "DESC";
    private String orderStatus;

    public String toString(){
      StringBuilder builder = new StringBuilder();
      builder.append(" o.orderDate ").append(orderDate);
      if (!isNull(payAmount))
        builder.append(" o.payment.paymentAmounts ").append(payAmount);

      if (!isNull(orderStatus))
        builder.append(" o.payment.paymentAmounts ").append(orderStatus);

      return builder.toString();
    }
  }


}
