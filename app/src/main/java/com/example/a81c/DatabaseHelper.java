package com.example.a81c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    public static final String TABLE_LOGIN = "login";
    public static final String COLUMN_LOGIN_ID = "id";
    public static final String COLUMN_LOGIN_USER_ID = "user_id";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT"
            + ")";

    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "("
            + COLUMN_LOGIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_LOGIN_USER_ID + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_LOGIN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    public String getColumnUserId() {
        return COLUMN_USER_ID;
    }

    public String getColumnUserName() {
        return COLUMN_USER_NAME;
    }

    public String getColumnUserEmail() {
        return COLUMN_USER_EMAIL;
    }

    public String getColumnUserPassword() {
        return COLUMN_USER_PASSWORD;
    }
}
