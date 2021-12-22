package com.ebuild.commerce.config;

import com.ebuild.commerce.business.company.domain.Company;
import com.ebuild.commerce.business.company.domain.SettlementInfo;
import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.common.Address;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class DataInitialization {

  private final JpaCompanyRepository jpaCompanyRepository;

  @PostConstruct
  public void init(){

    /*****************
     **** 회사 등록 ****
     *****************/
    Company company = Company.builder()
        .id(1L)
        .name("seller company")
        .registrationNumber("000-000000-000")
        .representativeNumber("010-0000-0000")
        .address(Address.builder()
            .baseAddress("기본 주소지")
            .detailAddress("상세 주소")
            .addressZipAddress("67812")
            .build())
        .settlementInfo(SettlementInfo.builder()
            .accountNumber("69123123-12343123")
            .bank("국민")
            .day(1)
            .build())
        .build();
    jpaCompanyRepository.save(company);
  }


}
