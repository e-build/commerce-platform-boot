package com.ebuild.commerce.business.user.buyer.domain.dto;

import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveResDto;
import com.ebuild.commerce.common.dto.AddressSaveReqDto;
import com.ebuild.commerce.common.dto.AddressSaveResDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BuyerResDto {

  private final Long buyerId;
  private final CommerceUserSaveResDto commerceUser;
  private final AddressSaveResDto receivingAddress;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  @Builder
  public BuyerResDto(Buyer buyer){
    this.buyerId = buyer.getId();
    this.commerceUser = new CommerceUserSaveResDto(buyer.getCommerceUserDetail());
    this.receivingAddress = new AddressSaveResDto(buyer.getReceivingAddress());
    this.createdAt = buyer.getCreatedAt();
    this.updatedAt = buyer.getUpdatedAt();

  }
}
