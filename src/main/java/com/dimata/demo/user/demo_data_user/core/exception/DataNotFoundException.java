package com.dimata.demo.user.demo_data_user.core.exception;

/**
 * Gunakan exception ini jika data tidak tersedia di database.
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5558238161985653783L;
    private static final String DEFAULT_POSTFIX = " tidak ditemukan";
    private final String message;

    public DataNotFoundException(String parameter) {
        super(parameter);
        this.message = parameter;
    }

    public DataNotFoundException(String prefix, String position) {
        this.message = prefix + " di " + position + DEFAULT_POSTFIX;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
