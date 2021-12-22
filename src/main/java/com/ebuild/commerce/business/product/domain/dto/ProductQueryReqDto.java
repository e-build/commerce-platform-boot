package com.ebuild.commerce.business.product.domain.dto;

import com.ebuild.commerce.business.product.domain.common.ProductStatus;
import java.time.LocalDate;

public class ProductQueryReqDto {

  private Long companyId;
  private LocalDate saleStartDate;
  private LocalDate saleEndDate;
  private ProductStatus productStatus;

  private Integer page;
  private Integer pageSize;

}
