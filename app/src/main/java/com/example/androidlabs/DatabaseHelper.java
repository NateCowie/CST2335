package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Chat.db";
    public static final int VERSION_NUM = 2;
    public static final String TABLE_NAME = "Message_Table";
    public static final String COL_MESSAGE = "Message";
    public static final String COL_ISSEND = "IsSend";
    public static final String COL_MESSAGEID = "MessageID";



    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+COL_MESSAGEID+"" +
            " INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_MESSAGE+" TEXT, "+COL_ISSEND+" TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }


    }








