package com.ebuild.commerce.business.product.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class ProductSearchParamDto {

  private final Long id;
  private final String name;
  private final List<String> productStatusList;
  private final Integer normalAmountGoe;
  private final Integer normalAmountLoe;
  private final Integer saleAmountGoe;
  private final Integer saleAmountLoe;
  private final List<Long> categoryIdList;
  private final Integer quantityGoe;
  private final Integer quantityLoe;
  private final String companyName;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleStartDateGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleStartDateLoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleEndDateGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleEndDateLoe;
  @DateTimeFormat(iso = ISO.DATE)
  private final LocalDateTime createdAtGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private final LocalDateTime createdAtLoe;
  @DateTimeFormat(iso = ISO.DATE)
  private final LocalDateTime updatedAtGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private final LocalDateTime updatedAtLoe;

}
