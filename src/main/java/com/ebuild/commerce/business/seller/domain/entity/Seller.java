package com.ebuild.commerce.business.seller.domain.entity;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.BaseEntity;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private AppUserDetails appUserDetails;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Company company;

  @Embedded
  private Address shippingAddress;

//  @OneToMany(mappedBy = "seller")
//  private List<Order> ordersList;

  @Builder
  public Seller(
      AppUserDetails appUserDetails
      , Company company
      , Address shippingAddress)
  {
    this.appUserDetails = appUserDetails;
    this.company = company;
    this.shippingAddress = shippingAddress;
  }
}
