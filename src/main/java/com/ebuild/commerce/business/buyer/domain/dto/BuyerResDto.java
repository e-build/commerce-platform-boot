package com.ebuild.commerce.business.buyer.domain.dto;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.auth.domain.dto.AppUserSaveResDto;
import com.ebuild.commerce.common.dto.AddressSaveResDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BuyerResDto {

  private Long buyerId;
  private AppUserSaveResDto commerceUser;
  private AddressSaveResDto receivingAddress;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  public BuyerResDto(Buyer buyer) {
    this.buyerId = buyer.getId();
    this.commerceUser = new AppUserSaveResDto(buyer.getAppUserDetails());
    if (buyer.getReceivingAddress() != null) {
      this.receivingAddress = new AddressSaveResDto(buyer.getReceivingAddress());
    }
    this.createdAt = buyer.getCreatedAt();
    this.updatedAt = buyer.getUpdatedAt();
  }
}
