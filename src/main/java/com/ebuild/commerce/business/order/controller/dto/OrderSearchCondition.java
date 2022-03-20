package com.ebuild.commerce.business.order.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderSearchCondition {

  private Long id;
  private List<String> orderStatusList;
  private Long buyerId;
  private String buyerEmail;
  private String productName;
  private List<String> paymentMeansList;
  private Integer paymentAmountsGoe;
  private Integer paymentAmountsLoe;
  private LocalDateTime orderDateGoe;
  private LocalDateTime orderDateLoe;
  private LocalDateTime paymentDateTimeGoe;
  private LocalDateTime paymentDateTimeLoe;

}
