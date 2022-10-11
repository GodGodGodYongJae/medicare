package com.example.arrayintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    //--- sington 적용
    private static UserDatabaseHelper instance;
    public static synchronized  UserDatabaseHelper getInstance(Context context){
        if(instance == null) {
            instance = new UserDatabaseHelper(context, "Mes", null ,1);
        }
        return instance;
    }
    //---
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Mes.db";
    public static final String TABLE_NAME ="test2";
    public static final String COLUMN_IDX = "data_idx2";
    public static final String COLUMN_DATA = "data_2";
    public static final String COLUMN_DAY = "scan_day2";
    public static final String COLUMN_ID = "scan_id2";
    public static final String COLUMN_DUPLE = "scan_duple";
    public static final String SQL_CREATE_MES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_IDX + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATA + " TEXT, " +
                    COLUMN_DAY + " TEXT, " +
                    COLUMN_ID + " TEXT, " +
                    COLUMN_DUPLE + " TEXT" +
                    ");";

    public UserDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > 1){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME);
        }
    }
}
