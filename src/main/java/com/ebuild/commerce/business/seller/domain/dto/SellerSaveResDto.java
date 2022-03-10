package com.ebuild.commerce.business.seller.domain.dto;

import com.ebuild.commerce.business.company.domain.dto.CompanyResDto;
import com.ebuild.commerce.business.auth.domain.dto.AppUserSaveResDto;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.common.dto.AddressSaveResDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SellerSaveResDto {

  private final Long sellerId;
  private final AppUserSaveResDto commerceUser;
  private final AddressSaveResDto shippingAddress;
  private final CompanyResDto company;
  private final LocalDateTime createdAt;

  @Builder
  public SellerSaveResDto(Seller seller) {
    this.sellerId = seller.getId();
    this.commerceUser = new AppUserSaveResDto(seller.getAppUserDetails());
    this.shippingAddress = new AddressSaveResDto(seller.getShippingAddress());
    this.company = new CompanyResDto(seller.getCompany());
    this.createdAt = seller.getCreatedAt();
  }
}
