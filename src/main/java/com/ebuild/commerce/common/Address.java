package com.ebuild.commerce.common;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

  private String baseAddress;
  private String detailAddress;
  private String addressZipCode;

  @Builder
  public Address(String baseAddress, String detailAddress, String addressZipCode) {
    this.baseAddress = baseAddress;
    this.detailAddress = detailAddress;
    this.addressZipCode = addressZipCode;
  }

}
