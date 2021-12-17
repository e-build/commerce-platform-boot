package com.ebuild.commerce.business.product.domain.common;

public enum ProductStatus {

  SALE, STOP;

  public String value(){
    return value();
  }

  public static ProductStatus fromValue(String value){
    return valueOf(value);
  }

}
