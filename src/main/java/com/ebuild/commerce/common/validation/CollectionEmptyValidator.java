package com.ebuild.commerce.common.validation;

import java.util.Collection;
import java.util.Collections;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

public class CollectionEmptyValidator  implements ConstraintValidator<NotEmptyCollection, Collection<?>> {

  private NotEmptyCollection annotation;

  @Override
  public void initialize(NotEmptyCollection constraintAnnotation) {
    this.annotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(Collection<?> values, ConstraintValidatorContext context) {
    return !CollectionUtils.isEmpty(values);
  }
}
