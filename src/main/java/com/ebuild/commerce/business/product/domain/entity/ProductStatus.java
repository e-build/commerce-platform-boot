package com.ebuild.commerce.business.product.domain.entity;

import com.ebuild.commerce.common.NullableEnumValue;

public enum ProductStatus implements NullableEnumValue {

  SALE, STOP;

  public String value(){
    return name();
  }

  public static ProductStatus fromValue(String value){
    return valueOf(value);
  }

}
