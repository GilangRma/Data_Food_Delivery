package com.dimata.demo.user.demo_data_user.core.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidation implements ConstraintValidator<PhoneNumberVal, String> {

    private static final String INDO_PHONE_REGEX = "^\\+\\d{1,3}\\d{1,14}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Pattern pattern = Pattern.compile(INDO_PHONE_REGEX);
        return pattern.matcher(value).matches();
    }
}
