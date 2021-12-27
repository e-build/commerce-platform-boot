package com.ebuild.commerce.business.company.domain.entity;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor
public class SettlementInfo {

  private String accountNumber;
  private String bank;
  private Integer day;

  public SettlementInfo(String accountNumber, String bank, Integer day) {
    this.accountNumber = accountNumber;
    this.bank = bank;
    this.day = day;
  }
}
