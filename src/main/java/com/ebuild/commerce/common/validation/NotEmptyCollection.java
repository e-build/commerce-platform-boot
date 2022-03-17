package com.ebuild.commerce.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {CollectionEmptyValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyCollection {
  String message() default "빈값은 허용되지 않습니다..";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  boolean ignoreCase() default false;

}
