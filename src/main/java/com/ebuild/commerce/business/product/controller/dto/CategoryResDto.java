package com.ebuild.commerce.business.product.controller.dto;

import com.ebuild.commerce.business.product.domain.entity.Category;
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

  public static CategoryResDto of(Category category) {
    return CategoryResDto.builder()
        .id(category.getId())
        .name(category.getName())
        .code(category.getCode())
        .superCode(category.getSuperCode())
        .build();
  }
}
