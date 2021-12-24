package com.ebuild.commerce.business.product.domain.dto;

import com.ebuild.commerce.business.product.domain.common.ProductStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class ProductQueryReqDto {

  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleStartDate;

  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate saleEndDate;

  private ProductStatus productStatus;

  private Integer page;

  private Integer pageSize;

}
