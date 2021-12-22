package com.ebuild.commerce.business.company.domain;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Embeddable
public class SettlementInfo {

  private Integer day;
  private String bank;
  private String accountNumber;

}
