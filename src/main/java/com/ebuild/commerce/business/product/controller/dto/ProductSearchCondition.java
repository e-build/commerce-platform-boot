package com.ebuild.commerce.business.product.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@NoArgsConstructor
public class ProductSearchCondition {

  private Long id;
  private String name;
  private List<String> productStatusList;
  private Integer normalAmountGoe;
  private Integer normalAmountLoe;
  private Integer saleAmountGoe;
  private Integer saleAmountLoe;
  private List<Long> categoryIdList;
  private Integer quantityGoe;
  private Integer quantityLoe;
  private String companyName;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleStartDateGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleStartDateLoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleEndDateGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleEndDateLoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime createdAtGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime createdAtLoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime updatedAtGoe;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime updatedAtLoe;

}
