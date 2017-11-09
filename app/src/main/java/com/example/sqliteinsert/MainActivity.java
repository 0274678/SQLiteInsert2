package com.example.sqliteinsert;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.sqliteinsert.data.UserContract;
import com.example.sqliteinsert.data.myDbAdapter;

public class MainActivity extends AppCompatActivity {
    EditText editName, editPass, currentName, newName, deleteUsername;
    myDbAdapter helper;
    ContentResolver userProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.editName);
        editPass = (EditText) findViewById(R.id.editPass);
        currentName = (EditText) findViewById(R.id.currentName);
        newName = (EditText) findViewById(R.id.newName);
        deleteUsername = (EditText) findViewById(R.id.deleteUsernameText);
        helper = new myDbAdapter(this);
        userProvider = getContentResolver();
    }

    public void viewData(View view) {
        Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
        startActivity(intent);
    }

    public void addUser(View view)
    {
        try {
            String editNameText = editName.getText().toString();
            String editPassText = editPass.getText().toString();

            ContentValues values = new ContentValues();
            values.put(UserContract.UserEntity.USER_NAME, editNameText);
            values.put(UserContract.UserEntity.USER_PWD, editPassText);

            userProvider.insert(UserContract.UserEntity.CONTENT_URI, values);

            Message.message(this,"Successfully added the user " + editNameText);
        } catch (Exception ex) {
            Message.message(this,ex.getMessage());
        }
    }

    public void deleteUser(View view)
    {
        try {
            String deleteUsernameText = deleteUsername.getText().toString();

            ContentValues values = new ContentValues();
            values.put(UserContract.UserEntity.USER_NAME, deleteUsernameText);

            userProvider.delete(UserContract.UserEntity.CONTENT_URI, UserContract.UserEntity.USER_NAME + " = ?", new String[] { deleteUsernameText });

            Message.message(this,"Successfully deleted the user " + deleteUsernameText);
        } catch (Exception ex) {
            Message.message(this,ex.getMessage());
        }
    }

    public void updateUser(View view) {
        try {
            String currentNameText = currentName.getText().toString();
            String newNameText = newName.getText().toString();

            ContentValues values = new ContentValues();
            values.put(UserContract.UserEntity.USER_NAME, newNameText);

            userProvider.update(UserContract.UserEntity.CONTENT_URI, values, UserContract.UserEntity.USER_NAME + " = ?", new String[] { currentNameText });

            Message.message(this,"Successfully updated username to " + newNameText);
        } catch (Exception ex) {
            Message.message(this,ex.getMessage());
        }
    }
}
