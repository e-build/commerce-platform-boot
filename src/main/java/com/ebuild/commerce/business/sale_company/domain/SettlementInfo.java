package com.ebuild.commerce.business.sale_company.domain;

import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class SettlementInfo {

  private Integer day;
  private String bank;
  private String accountNumber;

}
