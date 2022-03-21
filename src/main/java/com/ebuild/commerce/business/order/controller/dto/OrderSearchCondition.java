package com.ebuild.commerce.business.order.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime orderDateGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime orderDateLoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime paymentDateTimeGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime paymentDateTimeLoe;

}
