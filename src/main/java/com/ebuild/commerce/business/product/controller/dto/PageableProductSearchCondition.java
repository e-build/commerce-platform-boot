package com.ebuild.commerce.business.product.controller.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageableProductSearchCondition {

  private ProductSearchParamDto params;
  private Pageable pageable;

}
