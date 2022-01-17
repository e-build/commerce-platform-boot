package com.ebuild.commerce.business.company.domain.entity;

import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String registrationNumber;

  private String representativeNumber;

  @Embedded
  private Address address;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
  private List<Product> productList = Lists.newArrayList();

  @Embedded
  private SettlementInfo settlementInfo;

  @Builder
  public Company(String name, String registrationNumber
      , String representativeNumber, Address address
      , SettlementInfo settlementInfo) {

    this.name = name;
    this.registrationNumber = registrationNumber;
    this.representativeNumber = representativeNumber;
    this.address = address;
    this.settlementInfo = settlementInfo;
  }
}
