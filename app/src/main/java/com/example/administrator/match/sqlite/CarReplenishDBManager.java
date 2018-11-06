package com.example.administrator.match.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CarReplenishDBManager {
    private CarReplenishDBHelper helper;
    private SQLiteDatabase db;

    public CarReplenishDBManager(Context context) {
       helper = new CarReplenishDBHelper(context);

      db = helper.getWritableDatabase();
    }

    /**
     * 添加记录
     */
    public void addRecord(List<CarRecordBean> list){
        db.beginTransaction();

        try {
            for (CarRecordBean bean : list){
                db.execSQL("INSERT INTO record(carId,money,people,time) VALUES (?,?,?,?)",new Object[]{bean.getCarId(),bean.getMoney(),bean.getPeople(),bean.getTime()});
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();	//结束事务
        }
    }

    /**
     * 查询记录
     */
    public List<CarRecordBean> queryRecord(){
        ArrayList<CarRecordBean> carList = new ArrayList<CarRecordBean>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()){
            CarRecordBean carBean = new CarRecordBean();
            carBean.setId(c.getInt(c.getColumnIndex("id")));
            carBean.setCarId(c.getString(c.getColumnIndex("carId")));
            carBean.setMoney(c.getString(c.getColumnIndex("money")));
            carBean.setPeople(c.getString(c.getColumnIndex("people")));
            carBean.setTime(c.getString(c.getColumnIndex("time")));
            carList.add(carBean);
        }
        c.close();
        return carList;
    }

    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM record",null);
        return c;
    }

    public void closeDB(){
        db.close();
    }

}
