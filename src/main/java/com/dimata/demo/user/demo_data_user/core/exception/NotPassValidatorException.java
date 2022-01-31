package com.dimata.demo.user.demo_data_user.core.exception;

/**
 * Gunakan exception ini jika parameter tidak lulus validasi.
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
public class NotPassValidatorException extends RuntimeException{
	
	private static final long serialVersionUID = 163076212118364345L;
	
	private static final String BASE_MESSAGE = "Tidak valid";
    private final String parameter;
    private final String message;

    public NotPassValidatorException(String parameter, String message) {
        this.parameter = parameter;
        if(message == null || message.isEmpty()){
            this.message = BASE_MESSAGE;
        }else {
            this.message = message;
        }

    }

    @Override
    public String getMessage() {
        return parameter + ", " + message;
    }
}
