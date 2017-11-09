package com.example.sqliteinsert;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sqliteinsert.data.UserContract;

public class ViewDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        renderUsersTable();
    }

    private void renderUsersTable() {
        String[] projection = {
                UserContract.UserEntity._ID,
                UserContract.UserEntity.USER_NAME,
                UserContract.UserEntity.USER_PWD
        };

        Cursor cursor = getContentResolver().query(UserContract.UserEntity.CONTENT_URI, projection, null, null, null);

        TextView users = (TextView) findViewById(R.id.users);

        try {
            users.setText("" + cursor.getCount() + " users found!\n\n");
            users.append(UserContract.UserEntity.USER_NAME + "  -  " + UserContract.UserEntity.USER_PWD + "\n\n");

            while (cursor.moveToNext()) {
                String currentName = cursor.getString(cursor.getColumnIndex(UserContract.UserEntity.USER_NAME));
                String currentPassword = cursor.getString(cursor.getColumnIndex(UserContract.UserEntity.USER_PWD));

                users.append(currentName + "  -  " + currentPassword + "\n");
            }
        } finally {
            cursor.close();
        }
    }
}
