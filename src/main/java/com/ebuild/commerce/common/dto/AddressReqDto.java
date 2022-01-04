package com.ebuild.commerce.common.dto;

import com.ebuild.commerce.common.Address;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddressReqDto {

    @NotBlank
    private String baseAddress;
    @NotBlank
    private String detailAddress;
    @NotBlank
    private String addressZipCode;

    public Address get(){
      return Address.builder()
          .baseAddress(baseAddress)
          .detailAddress(detailAddress)
          .addressZipCode(addressZipCode)
          .build();
    }

}
