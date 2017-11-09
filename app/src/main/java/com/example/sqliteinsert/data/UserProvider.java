package com.example.sqliteinsert.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Jordy on 09-11-17.
 */

public class UserProvider extends ContentProvider {

    public static final String LOG_TAG = UserProvider.class.getSimpleName();

    private static final int USERS = 100;

    private static final int USER_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS, USERS);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS + "/*", USER_ID);
    }

    private myDbAdapter mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new myDbAdapter(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                cursor = database.query(UserContract.UserEntity.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case USER_ID:
                selection = UserContract.UserEntity.UID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(UserContract.UserEntity.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return insertUser(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertUser(Uri uri, ContentValues values) {
        String name = values.getAsString(UserContract.UserEntity.USER_NAME);
        if (name == null) {
            throw new IllegalArgumentException("User requires a name");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(UserContract.UserEntity.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return updateUser(uri, contentValues, selection, selectionArgs);
            case USER_ID:
                selection = UserContract.UserEntity.UID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateUser(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(UserContract.UserEntity.USER_NAME)) {
            String name = values.getAsString(UserContract.UserEntity.USER_NAME);
            if (name == null) {
                throw new IllegalArgumentException("User requires a name");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        return database.update(UserContract.UserEntity.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return database.delete(UserContract.UserEntity.TABLE_NAME, selection, selectionArgs);
            case USER_ID:
                selection = UserContract.UserEntity.UID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(UserContract.UserEntity.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return UserContract.UserEntity.CONTENT_LIST_TYPE;
            case USER_ID:
                return UserContract.UserEntity.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
