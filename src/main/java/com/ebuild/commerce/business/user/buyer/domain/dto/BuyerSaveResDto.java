package com.ebuild.commerce.business.user.buyer.domain.dto;

import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BuyerSaveResDto {
  
  private Long id;
  private String email;
  private String nickname;
  private String phoneNumber;

  private String baseAddress;
  private String detailAddress;
  private String addressZipAddress;

  private LocalDateTime createdAt;

  public static BuyerSaveResDto of(Buyer buyer){
    BuyerSaveResDto dto = new BuyerSaveResDto();
    dto.id = buyer.getCommerceUserDetail().getId();
    dto.email = buyer.getCommerceUserDetail().getEmail();
    dto.nickname = buyer.getCommerceUserDetail().getNickname();
    dto.phoneNumber = buyer.getCommerceUserDetail().getPhoneNumber();
    dto.baseAddress = buyer.getReceivingAddress().getBaseAddress();
    dto.detailAddress = buyer.getReceivingAddress().getDetailAddress();
    dto.addressZipAddress = buyer.getReceivingAddress().getAddressZipCode();
    dto.createdAt = buyer.getCreatedAt();
    return dto;
  }



}
