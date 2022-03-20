package com.ebuild.commerce.business.product.controller.dto;

import static com.ebuild.commerce.util.NullUtils.nullableValue;

import com.ebuild.commerce.business.company.domain.dto.CompanyResDto;
import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.product.domain.entity.Category;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResDto {

  private final Long id;
  private final String name;
  private final String productStatus;
  private final Long normalAmount;
  private final Long saleAmount;
  private final CategoryResDto category;
  private final LocalDate saleStartDate;
  private final LocalDate saleEndDate;
  private final Long quantity;
  private final CompanyResDto company;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  @QueryProjection
  public ProductResDto(Long id, String name, ProductStatus productStatus, Long normalAmount,
      Long saleAmount, Category category, LocalDate saleStartDate, LocalDate saleEndDate,
      Long quantity, Company company, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.productStatus = productStatus.value();
    this.normalAmount = normalAmount;
    this.saleAmount = saleAmount;
    this.category = CategoryResDto.of(category);
    this.saleStartDate = saleStartDate;
    this.saleEndDate = saleEndDate;
    this.quantity = quantity;
    this.company = CompanyResDto.builder().company(company).build();
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Builder
  public ProductResDto(Long id, String name, String productStatus,
      Long normalAmount, Long saleAmount, CategoryResDto category,
      LocalDate saleStartDate, LocalDate saleEndDate,
      Long quantity, CompanyResDto company,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.productStatus = productStatus;
    this.normalAmount = normalAmount;
    this.saleAmount = saleAmount;
    this.category = category;
    this.saleStartDate = saleStartDate;
    this.saleEndDate = saleEndDate;
    this.quantity = quantity;
    this.company = company;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static ProductResDto of(Product product) {
    return ProductResDto.builder()
        .id(product.getId())
        .name(product.getName())
        .productStatus(nullableValue(product.getProductStatus()))
        .normalAmount(product.getNormalAmount())
        .saleAmount(product.getSaleAmount())
        .category(CategoryResDto.of(product.getCategory()))
        .saleStartDate(product.getSaleStartDate())
        .saleEndDate(product.getSaleEndDate())
        .quantity(product.getQuantity())
        .company(CompanyResDto.builder().company(product.getCompany()).build())
        .createdAt(product.getCreatedAt())
        .updatedAt(product.getUpdatedAt())
        .build();
  }

}
