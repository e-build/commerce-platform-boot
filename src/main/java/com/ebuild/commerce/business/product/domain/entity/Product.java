package com.ebuild.commerce.business.product.domain.entity;

import com.ebuild.commerce.business.company.domain.Company;
import com.ebuild.commerce.business.product.domain.common.ProductCategory;
import com.ebuild.commerce.business.product.domain.common.ProductStatus;
import com.ebuild.commerce.business.product.domain.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.common.BaseEntity;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private ProductStatus productStatus;

  @Enumerated(EnumType.STRING)
  private ProductCategory category;

  private Integer normalAmount;

  private Integer saleAmount;

  private LocalDate saleStartDate;

  private LocalDate saleEndDate;

  private Integer quantity;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  public static Product create(JpaProductRepository jpaProductRepository, Company company, ProductSaveReqDto productSaveReqDto) {
    if ( isExists(jpaProductRepository, company, productSaveReqDto) )
      throw new AlreadyExistsException("["+productSaveReqDto.getProduct().getName()+"] 은 이미 존재하는 상품명입니다.");

    Product product = of(productSaveReqDto);
    product.registerCompany(company);
    return product;
  }

  public void update(JpaProductRepository jpaProductRepository, Company company, ProductSaveReqDto productSaveReqDto) {
    if ( isExists(jpaProductRepository, company, productSaveReqDto)
        && !isSameProduct(productSaveReqDto.getProduct().getId()) )
      throw new AlreadyExistsException("["+productSaveReqDto.getProduct().getName()+"] 은 다른 상품에서 이미 사용중인 상품명입니다.");

    this.name = productSaveReqDto.getProduct().getName();
    this.productStatus = ProductStatus.fromValue(productSaveReqDto.getProduct().getProductStatus());
    this.category = ProductCategory.fromValue(productSaveReqDto.getProduct().getCategory());
    this.normalAmount = productSaveReqDto.getProduct().getNormalAmount();
    this.saleAmount = productSaveReqDto.getProduct().getSaleAmount();
    this.saleStartDate = productSaveReqDto.getProduct().getSaleStartDate();
    this.saleEndDate = productSaveReqDto.getProduct().getSaleEndDate();
    this.quantity = productSaveReqDto.getProduct().getQuantity();
  }

  private boolean isSameProduct(Long targetProductId) {
    return Objects.equals(this.id, targetProductId);
  }

  public void registerCompany(Company company){
    this.company = company;
  }

  private static boolean isExists(
      JpaProductRepository jpaProductRepository
      , Company company
      , ProductSaveReqDto productSaveReqDto) {
    return jpaProductRepository
        .findByCompanyAndName(company, productSaveReqDto.getProduct().getName())
        .isPresent();
  }

  private static Product of(ProductSaveReqDto productSaveReqDto){
    return Product.builder()
        .id(productSaveReqDto.getProduct().getId())
        .name(productSaveReqDto.getProduct().getName())
        .productStatus(ProductStatus.fromValue(productSaveReqDto.getProduct().getProductStatus()))
        .category(ProductCategory.fromValue(productSaveReqDto.getProduct().getCategory()))
        .normalAmount(productSaveReqDto.getProduct().getNormalAmount())
        .saleAmount(productSaveReqDto.getProduct().getSaleAmount())
        .saleStartDate(productSaveReqDto.getProduct().getSaleStartDate())
        .saleEndDate(productSaveReqDto.getProduct().getSaleEndDate())
        .quantity(productSaveReqDto.getProduct().getQuantity())
        .build();
  }
}
