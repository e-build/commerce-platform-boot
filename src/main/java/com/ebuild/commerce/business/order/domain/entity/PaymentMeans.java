package com.ebuild.commerce.business.order.domain.entity;

public enum PaymentMeans {

  CREDIT_CARD, CASH;

  public static PaymentMeans fromValue(String value){
    return valueOf(value);
  }
}
