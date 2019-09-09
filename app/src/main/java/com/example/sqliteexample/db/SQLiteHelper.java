package com.example.sqliteexample.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.sqliteexample.Utils.CREATEDBY;
import static com.example.sqliteexample.Utils.FIRST_APP;
import static com.example.sqliteexample.Utils.ID;
import static com.example.sqliteexample.Utils.NAME;
import static com.example.sqliteexample.Utils.PUBLISHER;
import static com.example.sqliteexample.Utils.REALNAME;
import static com.example.sqliteexample.Utils.TABLE_NAME;
import static com.example.sqliteexample.Utils.URL;

public class SQLiteHelper extends SQLiteOpenHelper {
public static final String DATABASE_NAME= "herodb.sqlite";
private static final int DB_VERSION = 1;
    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
final String createTable= "create table "+TABLE_NAME+ "("+
        ID+ " integer primary key autoincrement,"+
        NAME+ " varchar(50),"+
        REALNAME+" varchar(50),"+
        FIRST_APP+" varchar(50),"+
        CREATEDBY+" varchar(20),"+
        PUBLISHER+" varchar(20),"+
        URL+" TEXT)";
sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
