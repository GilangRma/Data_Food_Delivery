package com.dimata.demo.user.demo_data_user.core.exception;

/**
 * Gunakan exception ini jika consumer salah memilih request. <br>
 * PUSH untuk menyimpan data baru.<br>
 * PUT untuk mengedit data yang ada.
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
public class UsePutOrPostException extends RuntimeException {

    private static final long serialVersionUID = 3410826745996668554L;

    private static final String BASE = "GUNAKAN PUT untuk update data / POST untuk tambah data baru";

    public UsePutOrPostException(String additionalInfo) {
        super(BASE + " ( " + additionalInfo + " )");
    }

    public UsePutOrPostException() {
        super(BASE);
    }
}
