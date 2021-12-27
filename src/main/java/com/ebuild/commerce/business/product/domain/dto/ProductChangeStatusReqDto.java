package com.ebuild.commerce.business.product.domain.dto;

import com.ebuild.commerce.business.product.domain.common.ProductStatus;
import com.ebuild.commerce.common.Enum;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductChangeStatusReqDto {

  @NotBlank(message = "상품 상태는 필수 입력 값입니다.")
  @Enum(enumClass = ProductStatus.class, ignoreCase = true, message = "SALE, STOP 값 중 하나를 입력해주시기 바랍니다.")
  private String productStatus;

}
