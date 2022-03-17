package com.ebuild.commerce.business.product.controller.dto;

import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class ProductSearchReqDto {

  private Long companyId;

  private String name;

  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleStartDate;

  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleEndDate;

//  @DateTimeFormat(pattern = "")
//  private LocalDateTime createdAtAfter;
//
//  @DateTimeFormat(pattern = "")
//  private LocalDateTime updatedAtAfter;

  private ProductStatus productStatus;

  private Pageable pageable;

  private String[] columns;

}
