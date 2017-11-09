package com.example.sqliteinsert.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class UserContract {

    public static final String CONTENT_AUTHORITY = "com.example.sqliteinsert";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USERS = "users";
    public final static String DATABASE_NAME = "employee";
    public final static int DATABASE_VERSION = 5;

    public final static class UserEntity implements BaseColumns {
        public final static String TABLE_NAME = "UserInfo";
        public final static String UID = BaseColumns._ID;
        public final static String USER_NAME = "UserName";
        public final static String USER_PWD = "Password";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

    }
}

