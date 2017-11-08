package com.example.sqliteinsert;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.sqliteinsert.data.UserContract;
import com.example.sqliteinsert.data.UserDbHelper;

public class myDbAdapter  {

    UserDbHelper helper;

    public myDbAdapter(Context context)
    {
        helper = new UserDbHelper(context);
    }

    public long insertData(String name, String password)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserEntity.USER_NAME, name);
        contentValues.put(UserContract.UserEntity.USER_PWD, password);
        long id = db.insert(UserContract.UserEntity.TABLE_NAME, null , contentValues);
        return id;
    }

}