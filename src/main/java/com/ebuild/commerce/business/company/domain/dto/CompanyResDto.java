package com.ebuild.commerce.business.company.domain.dto;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.company.domain.entity.SettlementInfo;
import com.ebuild.commerce.common.dto.AddressSaveResDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyResDto {

  private Long id;
  private String name;
  private String registrationNumber;
  private String representativeNumber;
  private AddressSaveResDto address;
  private SettlementInfo settlementInfo;

  @Builder
  public CompanyResDto(Company company){
    this.id = company.getId();
    this.address = new AddressSaveResDto(company.getAddress());
    this.registrationNumber = company.getRegistrationNumber();
    this.representativeNumber = company.getRepresentativeNumber();
    this.settlementInfo = company.getSettlementInfo();
  }

}
