package com.dimata.demo.user.demo_data_user.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonStatic {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static final String MAINTENANCE_PATH = "api/maintainer";
    public static final String ADMIN_PATH = "api/admin";
    public static final String USER_PATH = "api/user";
    public static final String MERCHANT_PATH = "api/merchant";

    public static final String MAINTENANCE_FILTER = "/" + MAINTENANCE_PATH + "/**";
    public static final String ADMIN_FILTER = "/" + ADMIN_PATH + "/**";
    public static final String USER_FILTER = "/" + USER_PATH + "/**";
    public static final String MERCHANT_FILTER = "/" + MERCHANT_PATH + "/**";

    public static final String STORAGE_PATH = "/storages";

    /// sys service setting key
    // storage
    public static final String STORAGE_PLATFORM = "STORAGE_PLATFORM";
    public static final String STORAGE_ACCESS_KEY = "STORAGE_ACCESS_KEY";
    public static final String STORAGE_SECRET_KEY = "STORAGE_SECRET_KEY";
    public static final String STORAGE_ENDPOINT = "STORAGE_ENDPOINT";
    public static final String STORAGE_DEFAULT_REGION = "STORAGE_DEFAULT_REGION";
    public static final String STORAGE_DEFAULT_BUCKET = "STORAGE_DEFAULT_BUCKET";

    /// Storage folder
    /// mapping storages folder
    public static final String CATEGORIES_ICON_FOLDER = "categories/icons";

    /// content type storage
    public static final String IMAGE_TYPE_CONTENT = "image/png";
    public static final String VIDEO_TYPE_CONTENT = "video/mp4";
}
