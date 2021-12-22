package com.ebuild.commerce.business.product.domain.entity;

import com.ebuild.commerce.business.product.domain.common.ProductCategory;
import com.ebuild.commerce.business.product.domain.common.ProductStatus;
import com.ebuild.commerce.business.company.domain.Company;
import com.ebuild.commerce.common.DateTimeAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
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
public class Product extends DateTimeAuditing {

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

  public void registerCompany(Company company){
    this.company = company;
  }

}
