package com.ebuild.commerce.business.product.domain.dto;

import static com.ebuild.commerce.util.NullUtils.nullableValue;

import com.ebuild.commerce.business.company.domain.dto.CompanyResDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResDto {

  private final Long id;
  private final String name;
  private final String productStatus;
  private final Integer normalAmount;
  private final Integer saleAmount;
  private final String category;
  private final LocalDate saleStartDate;
  private final LocalDate saleEndDate;
  private final Integer quantity;
  private final CompanyResDto company;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  @Builder
  public ProductResDto(Product product){
    this.id = product.getId();
    this.name = product.getName();
    this.productStatus = nullableValue(product.getProductStatus());
    this.normalAmount = product.getNormalAmount();
    this.saleAmount = product.getSaleAmount();
    this.category = nullableValue(product.getCategory());
    this.saleStartDate = product.getSaleStartDate();
    this.saleEndDate = product.getSaleEndDate();
    this.quantity = product.getQuantity();
    this.company = CompanyResDto.builder().company(product.getCompany()).build();
    this.createdAt = product.getCreatedAt();
    this.updatedAt = product.getUpdatedAt();
  }

}
