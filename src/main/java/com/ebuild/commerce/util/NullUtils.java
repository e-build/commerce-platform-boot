package com.ebuild.commerce.util;

import com.ebuild.commerce.common.NullableEnumValue;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NullUtils {

  public static String nullableValue(NullableEnumValue enumName){
    if (Objects.isNull(enumName) )
      return StringUtils.EMPTY;
    return enumName.value();
  }



}
