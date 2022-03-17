package com.ebuild.commerce.business.product.domain.dto;

import com.ebuild.commerce.business.product.domain.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResDto {

  private Long id;
  private String name;
  private String code;
  private String superCode;

  @Builder
  public CategoryResDto(Long id, String name, String code, String superCode) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.superCode = superCode;
  }

  public static CategoryResDto of(ProductCategory productCategory) {
    return CategoryResDto.builder()
        .id(productCategory.getCategory().getId())
        .name(productCategory.getCategory().getName())
        .code(productCategory.getCategory().getCode())
        .superCode(productCategory.getCategory().getSuperCode())
        .build();
  }
}
