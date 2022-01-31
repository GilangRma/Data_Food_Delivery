package com.dimata.demo.user.demo_data_user.core.util.validation;

import org.springframework.validation.annotation.Validated;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validasi no telp. Null di anggap true.
 */
@Documented
@Constraint(validatedBy = PhoneNumberValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Validated
public @interface PhoneNumberVal {
    String message() default "Wrong Phone number format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
