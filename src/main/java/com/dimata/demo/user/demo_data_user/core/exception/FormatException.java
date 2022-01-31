package com.dimata.demo.user.demo_data_user.core.exception;

/**
 * Gunakan exception ini jika terdapat format yang salah.
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
public class FormatException extends RuntimeException {

	private static final long serialVersionUID = 8990164132805617586L;

	public FormatException(String message) {
        super(message);
    }
}
