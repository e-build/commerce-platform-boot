package com.ebuild.commerce.business.product.controller.dto;

import static com.ebuild.commerce.util.NullUtils.nullableValue;

import com.ebuild.commerce.business.company.domain.dto.CompanyResDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResDto {

  private final Long id;
  private final String name;
  private final String productStatus;
  private final Integer normalAmount;
  private final Integer saleAmount;
  private final List<CategoryResDto> categoryList;
  private final LocalDate saleStartDate;
  private final LocalDate saleEndDate;
  private final Integer quantity;
  private final CompanyResDto company;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  @Builder
  public ProductResDto(Long id, String name, String productStatus, Integer normalAmount,
      Integer saleAmount,
      List<CategoryResDto> categoryList, LocalDate saleStartDate, LocalDate saleEndDate,
      Integer quantity, CompanyResDto company, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.productStatus = productStatus;
    this.normalAmount = normalAmount;
    this.saleAmount = saleAmount;
    this.categoryList = categoryList;
    this.saleStartDate = saleStartDate;
    this.saleEndDate = saleEndDate;
    this.quantity = quantity;
    this.company = company;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static ProductResDto of(Product product){
    return ProductResDto.builder()
        .id(product.getId())
        .name(product.getName())
        .productStatus(nullableValue(product.getProductStatus()))
        .normalAmount(product.getNormalAmount())
        .saleAmount(product.getSaleAmount())
        .categoryList(product.getCategoryList().stream().map(CategoryResDto::of).collect(Collectors.toList()))
        .saleStartDate(product.getSaleStartDate())
        .saleEndDate(product.getSaleEndDate())
        .quantity(product.getQuantity())
        .company(CompanyResDto.builder().company(product.getCompany()).build())
        .createdAt(product.getCreatedAt())
        .updatedAt(product.getUpdatedAt())
        .build();
  }

}
