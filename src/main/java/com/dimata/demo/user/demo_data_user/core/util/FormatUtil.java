package com.dimata.demo.user.demo_data_user.core.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import com.dimata.demo.user.demo_data_user.core.exception.FormatException;

/**
 * Alat bantu untuk melakukan format.
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
public class FormatUtil {

  private FormatUtil() {
  }

  public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String FORMAT_OF_DATE = "dd-MM-yyyy";
  public static final String FORMAT_OF_DATE_FOR_SQL_QUERY = "yyyy-MM-dd";
  public static final String DATE_FORMAT_FOR_SQL_QUERY = "yyyy-MM-ddTHH:MM:ss";

  public static LocalDateTime convertToLocalDate(String date, String dateFormat) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat).withResolverStyle(ResolverStyle.SMART);
      return LocalDateTime.parse(date, formatter);
    } catch (Exception e) {
      throw new FormatException("Format tanggal harus " + dateFormat + ". Contoh : 29-08-2002 12:10:00");
    }
  }

  public static LocalDate convertDateToLocalDate(String date, String dateFormat) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat).withResolverStyle(ResolverStyle.SMART);
      return LocalDate.parse(date, formatter);
    } catch (Exception e) {
      throw new FormatException("Format tanggal harus " + dateFormat + ". Contoh : 29-08-2002 12:10:00");
    }
  }

  public static LocalDateTime convertToLocalDate(String date) {
    return convertToLocalDate(date, DATE_FORMAT);
  }

  public static String convertToString(LocalDateTime date) {
    return convertToString(date, DATE_FORMAT);
  }

  public static String convertDateToString(LocalDate date) {
    return convertDateToString(date, FORMAT_OF_DATE);
  }

  public static String changeIDCountryCodeTo(String phoneNumber, String change) {
    return phoneNumber.replace("+62", change);
  }

  public static String convertToString(LocalDateTime date, String format) {
    Objects.requireNonNull(date, "Date can't be null");
    if (format == null || format.isBlank()) {
      throw new IllegalArgumentException("format date can't be null or blank");
    }
    var formatter = DateTimeFormatter.ofPattern(format);
    return formatter.format(date);
  }

  public static String convertDateToString(LocalDate date, String format) {
    Objects.requireNonNull(date, "Date can't be null");
    if (format == null || format.isBlank()) {
      throw new IllegalArgumentException("format date can't be null or blank");
    }
    var formatter = DateTimeFormatter.ofPattern(format);
    return formatter.format(date);
  }

  public static Date plusDateTime(Date startTime, long duration, ChronoUnit unit) {
    Instant start = startTime.toInstant();
    Instant result = start.plus(duration, unit);
    return Date.from(result);
  }

  public static Date minusDateTime(Date startTime, long duration, ChronoUnit unit) {
    Instant start = startTime.toInstant();
    Instant result = start.minus(duration, unit);
    return Date.from(result);
  }
}
