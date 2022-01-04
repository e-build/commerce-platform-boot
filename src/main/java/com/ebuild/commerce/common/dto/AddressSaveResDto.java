package com.ebuild.commerce.common.dto;

import com.ebuild.commerce.common.Address;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddressSaveResDto {

    private String baseAddress;
    private String detailAddress;
    private String addressZipCode;

    public AddressSaveResDto(Address entity) {
        this.baseAddress = entity.getBaseAddress();
        this.detailAddress = entity.getDetailAddress();
        this.addressZipCode = entity.getAddressZipCode();
    }

}
