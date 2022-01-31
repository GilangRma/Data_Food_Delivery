package com.dimata.demo.user.demo_data_user.core.exception;

import java.time.LocalDateTime;

import com.dimata.demo.user.demo_data_user.core.util.FormatUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pesan exception sederhana,
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage {
    private String ticket;
    private final String timestamp = FormatUtil.convertToString(LocalDateTime.now());
    private String status;
    private String httpCode;
    private String path;
    private String message;
    private String trace;
}
