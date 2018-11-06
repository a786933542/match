package com.example.administrator.match.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EnvironmentalDB extends SQLiteOpenHelper{


    public static final String TABLENAME="environmental";
    public static final String dbName="info.db";

    public EnvironmentalDB(Context context) {
        super(context, dbName, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+TABLENAME+"\n" +
                "(\n" +
                "pm INT ,\n" +
                "co2 INT ,\n" +
                "LightIntensity INT ,\n" +
                "humidity INT,\n" +
                "temperature INT ,\n" +
                "Status INT ,\n" +
                "createDate datetime"+
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
