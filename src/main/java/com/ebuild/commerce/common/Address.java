package com.ebuild.commerce.common;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

  private String baseAddress;
  private String detailAddress;
  private String addressZipAddress;

  public Address(String baseAddress, String detailAddress, String addressZipAddress) {
    this.baseAddress = baseAddress;
    this.detailAddress = detailAddress;
    this.addressZipAddress = addressZipAddress;
  }
}
