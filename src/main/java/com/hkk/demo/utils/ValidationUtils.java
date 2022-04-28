package com.hkk.demo.utils;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import lombok.experimental.UtilityClass;

/**
 * @author kang
 * @date 2022/4/22
 */
@UtilityClass
public class ValidationUtils {


    public static final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    public static <T> Set<ConstraintViolation<T>> validate(T t) {
        return validator.validate(t);
    }

}
