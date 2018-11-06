package com.example.administrator.match.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarReplenishDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "carReplenish.db";
    private static final int DATABASE_VERSION = 1;

    public CarReplenishDBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS record" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, carId VARCHAR, money VARCHAR, people VARCHAR, time VARCHAR)");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("ALTER TABLE record ADD COLUMN other STRING");
    }


}
