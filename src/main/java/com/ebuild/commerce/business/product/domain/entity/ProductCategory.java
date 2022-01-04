package com.ebuild.commerce.business.product.domain.entity;

import com.ebuild.commerce.common.NullableEnumValue;

public enum ProductCategory implements NullableEnumValue {

  BOOK, CLOTH;

  public String value(){
    return name();
  }

  public static ProductCategory fromValue(String value){
    return valueOf(value);
  }


}
