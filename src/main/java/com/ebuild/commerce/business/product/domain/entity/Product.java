package com.ebuild.commerce.business.product.domain.entity;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.product.controller.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.common.BaseEntity;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;


@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private ProductStatus productStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  private Long normalAmount;

  private Long saleAmount;

  private Integer shippingTime;

  private LocalDate saleStartDate;

  private LocalDate saleEndDate;

  private Long quantity;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  public static Product create(
      JpaProductRepository jpaProductRepository, Company company,
      Category category, ProductSaveReqDto productSaveReqDto) {
    if (isExists(jpaProductRepository, company, productSaveReqDto)) {
      throw new AlreadyExistsException(productSaveReqDto.getProduct().getName(), "상품명");
    }

    Product product = productSaveReqDto.toEntity();
    product.registerCompany(company);
    product.category = category;
    return product;
  }

  public void update(JpaProductRepository jpaProductRepository, Company company, Category category,
      ProductSaveReqDto productSaveReqDto) {
    if (isExists(jpaProductRepository, company, productSaveReqDto)
        && !isSameProduct(productSaveReqDto.getProduct().getId())) {
      throw new AlreadyExistsException(productSaveReqDto.getProduct().getName(), "상품명");
    }

    this.name = productSaveReqDto.getProduct().getName();
    this.productStatus = ProductStatus.fromValue(productSaveReqDto.getProduct().getProductStatus());
    this.normalAmount = productSaveReqDto.getProduct().getNormalAmount();
    this.saleAmount = productSaveReqDto.getProduct().getSaleAmount();
    this.saleStartDate = productSaveReqDto.getProduct().getSaleStartDate();
    this.saleEndDate = productSaveReqDto.getProduct().getSaleEndDate();
    this.quantity = productSaveReqDto.getProduct().getQuantity();
    this.category = category;
  }

  private boolean isSameProduct(Long targetProductId) {
    return Objects.equals(this.id, targetProductId);
  }

  public void registerCompany(Company company) {
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

  @Builder
  public Product(Long id, String name,
      ProductStatus productStatus,
      Category category, Long normalAmount, Long saleAmount,
      Integer shippingTime, LocalDate saleStartDate, LocalDate saleEndDate,
      Long quantity, Company company) {
    this.id = id;
    this.name = name;
    this.productStatus = productStatus;
    this.category = category;
    this.normalAmount = normalAmount;
    this.saleAmount = saleAmount;
    this.shippingTime = shippingTime;
    this.saleStartDate = saleStartDate;
    this.saleEndDate = saleEndDate;
    this.quantity = quantity;
    this.company = company;
  }

  public void changeSaleStatus(ProductStatus productStatus) {
    this.productStatus = productStatus;
  }

}
