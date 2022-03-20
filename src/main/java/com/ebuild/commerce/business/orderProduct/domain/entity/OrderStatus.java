package com.ebuild.commerce.business.orderProduct.domain.entity;

public enum OrderStatus {

  PAYED, DELIVERING, DELIVERED, CANCELING, CANCELED, PURCHASE_COMPLETE;

  public static OrderStatus fromValue(String s) {
    return valueOf(s.toUpperCase());
  }
}
