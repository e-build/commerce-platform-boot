package com.ebuild.commerce.business.product.domain.dto;

import static com.ebuild.commerce.util.NullUtils.*;

import com.ebuild.commerce.business.company.domain.Company;
import com.ebuild.commerce.business.product.domain.entity.Product;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductSaveResDto {

  private Long id;
  private String name;
  private String productStatus;
  private Integer normalAmount;
  private Integer saleAmount;
  private String category;
  private LocalDate saleStartDate;
  private LocalDate saleEndDate;
  private Integer quantity;
  private Company company;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static ProductSaveResDto of(Product product){
      return ProductSaveResDto.builder()
        .id(product.getId())
        .name(product.getName())
        .productStatus(nullableValue(product.getProductStatus()))
        .normalAmount(product.getNormalAmount())
        .saleAmount(product.getSaleAmount())
        .category(nullableValue(product.getCategory()))
        .saleStartDate(product.getSaleStartDate())
        .saleEndDate(product.getSaleEndDate())
        .quantity(product.getQuantity())
        .company(product.getCompany())
        .createdAt(product.getCreatedAt())
        .updatedAt(product.getUpdatedAt())
        .build();
  }

}
