package com.dimata.demo.user.demo_data_user.core.util;

import java.util.Collection;

import io.r2dbc.spi.Row;

/**
 * Tools object untuk melakukan hal yang berhubungan dengan manipulasi.
 *
 * @author Hariyogi
 * @since 2 Sep 2020
 */
public class ManipulateUtil {

    private ManipulateUtil() {
    }

    /**
     * Method singkat dari <br>
     * return (firstObject != null) ? firstObject : secondObject;
     *
     * @param firstObject  Object yang direturn jika ini tidak null.
     * @param secondObject Object yang direturn jika firstObject null.
     * @param <T>          Semua jenis Object.
     */
    public static <T> T changeItOrNot(T firstObject, T secondObject) {
        if (firstObject != null) {
            return firstObject;
        }
        return secondObject;
    }

    /**
     * Mengubah boolean ke integer.
     * 1 = true, 0 = false
     */
    public static int changeBoolToInteger(boolean bool) {
        return (bool) ? CommonStatic.TRUE : CommonStatic.FALSE;
    }

    /**
     * Mengubah integer ke boolean
     * 1 > 0 true, 0 <= false
     */
    public static boolean changeIntegerToBool(int integer) {
        return integer > 0;
    }

    /**
     * Membuat String dalam bentuk array. Contoh : Ayam, Macan, Sampi.
     *
     * @param strings Kumpulan String
     * @return hasil seperti contoh.
     */
    public static String listStringToString(Collection<String> strings) {
        StringBuilder builder = new StringBuilder();
        if (!strings.isEmpty()) {
            int size = strings.size() - 1;
            int pos = 0;
            for (String string : strings) {
                builder.append(string.toUpperCase());
                if (pos < size) {
                    builder.append(",");
                }
                pos++;
            }
        }
        return builder.toString();
    }

    public static <T> T parseRow(Row row, String collumName, Class<T> type) {
        try {
            return row.get(collumName, type);
        }catch (Exception e) {
            return null;
        }
    }
}
