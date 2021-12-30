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

  private Long id;
  private String name;
  private String productStatus;
  private Integer normalAmount;
  private Integer saleAmount;
  private String category;
  private LocalDate saleStartDate;
  private LocalDate saleEndDate;
  private Integer quantity;
  private CompanyResDto company;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

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
