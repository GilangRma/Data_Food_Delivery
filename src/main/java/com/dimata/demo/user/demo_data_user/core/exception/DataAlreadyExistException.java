package com.dimata.demo.user.demo_data_user.core.exception;

/**
 * Gunakan exception ini jika data sudah tersedia di database.
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
public class DataAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 2584965928048952730L;
    private static final String DEFAULT_POSTFIX = " sudah tersedia";

    public DataAlreadyExistException(String param) {
        super(param);
    }

    public DataAlreadyExistException(String prefix, String position) {
        super(prefix + " di " + position + DEFAULT_POSTFIX);
    }

}
