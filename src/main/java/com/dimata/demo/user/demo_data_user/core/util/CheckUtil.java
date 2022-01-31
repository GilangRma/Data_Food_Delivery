package com.dimata.demo.user.demo_data_user.core.util;


import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.dimata.demo.user.demo_data_user.core.exception.FormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckUtil {

    @Autowired
    private Validator validator;

    private static final String INDO_PHONE_REGEX = "^\\+\\d{1,3}\\d{1,14}$";

    public static boolean isStringBlank(String source) {
        return source == null || source.isBlank();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(INDO_PHONE_REGEX);
        return pattern.matcher(phoneNumber).matches();
    }

    public static boolean isDateFormatValid(String date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(FormatUtil.DATE_FORMAT)
                .withResolverStyle(ResolverStyle.SMART);
        try {
            dateFormat.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public <T> boolean validateEntity(T entity) {
        Set<ConstraintViolation<T>> validation = validator.validate(entity);
        if (!validation.isEmpty()) {
            int count = 1;
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            for (ConstraintViolation<T> item : validation) {
                builder.append(item.getMessage());
                if (count < validation.size()) {
                    builder.append(",");
                }
                count++;
            }
            builder.append(")");
            throw new FormatException(builder.toString());
        } else {
            return true;
        }
    }
}
