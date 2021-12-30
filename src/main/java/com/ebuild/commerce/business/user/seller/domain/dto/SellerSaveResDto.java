package com.ebuild.commerce.business.user.seller.domain.dto;

import com.ebuild.commerce.business.company.domain.dto.CompanyResDto;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveResDto;
import com.ebuild.commerce.business.user.seller.domain.entity.Seller;
import com.ebuild.commerce.common.dto.AddressSaveResDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SellerSaveResDto {

  private final Long sellerId;
  private final CommerceUserSaveResDto commerceUser;
  private final AddressSaveResDto shippingAddress;
  private final CompanyResDto company;
  private final LocalDateTime createdAt;

  @Builder
  public SellerSaveResDto(Seller seller) {
    this.sellerId = seller.getId();
    this.commerceUser = new CommerceUserSaveResDto(seller.getCommerceUserDetail());
    this.shippingAddress = new AddressSaveResDto(seller.getShippingAddress());
    this.company = new CompanyResDto(seller.getCompany());
    this.createdAt = seller.getCreatedAt();
  }
}
