package com.dimata.demo.user.demo_data_user.core.util.validation;

import org.springframework.validation.annotation.Validated;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Validated
public @interface DateFormatVal {
    String message() default "Wrong Date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
