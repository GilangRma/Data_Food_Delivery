package com.dimata.demo.user.demo_data_user.core.exception;

/**
 * Gunakan Exeption ini jika users tidak diizinkan masuk. Contoh : Password salah.
 */
public class UnauthorizedAccessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 964679676956735086L;

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
