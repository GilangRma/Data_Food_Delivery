package com.dimata.demo.user.demo_data_user.core.exception;

/**
 * Gunakan exception ini jika data1 dan data2 sama.
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
public class SameDataException extends RuntimeException {
   
	private static final long serialVersionUID = 7837872190148148704L;
	
	private static final String BASE_INFO = "Data tidak boleh sama";

    public SameDataException(String data1, String data2) {
        super(data1 + " == " + data2 + " " + BASE_INFO);
    }
}
