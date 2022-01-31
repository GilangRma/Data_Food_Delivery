package com.dimata.demo.user.demo_data_user.core.util.jackson;

import java.time.LocalDateTime;

import com.dimata.demo.user.demo_data_user.core.util.FormatUtil;
import com.fasterxml.jackson.databind.util.StdConverter;

public class TimeSerialize extends StdConverter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime time) {
        return FormatUtil.convertToString(time);
    }
}
