package com.ebuild.commerce.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class EnumValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues == null)
            return false;
        return validateEnumValues(value, enumValues);
    }

    private boolean validateEnumValues(String value, Object[] enumValues) {
        for (Object enumValue : enumValues) {
            if (equalsEnumValue(value, enumValue))
                return true;
        }
        return false;
    }

    private boolean equalsEnumValue(String value, Object enumValue) {
        return StringUtils.equals(enumValue.toString(), value)
                || (this.annotation.ignoreCase() && StringUtils.equalsIgnoreCase(enumValue.toString(), value));
    }
}
