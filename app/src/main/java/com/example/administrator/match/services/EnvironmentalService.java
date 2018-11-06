package com.example.administrator.match.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.match.sqlite.EnvironmentalDB;
import com.example.administrator.match.domain.EnvironmentalBean;
import com.example.administrator.match.until.CacheUntil;
import com.example.administrator.match.until.NetUntil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EnvironmentalService extends Service {

    private String ip;
    private String port;
    private NetUntil netUntil;
    private EnvironmentalDB sql_environmental;
    private SQLiteDatabase database;
    public EnvironmentalService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ip= CacheUntil.getString(this,"ip","192.168.1.101");
        port=CacheUntil.getString(this,"port","8080");
        netUntil=new NetUntil();
        sql_environmental=new EnvironmentalDB(this);
        database= sql_environmental.getWritableDatabase();
        handler.sendEmptyMessage(0);
        Toast.makeText(this, "服务已开启,当前IP为"+ip+";端口号为"+port, Toast.LENGTH_SHORT).show();
        Log.e("services","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private List<EnvironmentalBean> beans=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Log.e("url","http://"+ip+":"+port+"/transportservice/type/jason/action/GetAllSense.do");
                    netUntil.getData("{}","http://"+ip+":"+port+"/transportservice/type/jason/action/GetAllSense.do",handler);
                    handler.sendEmptyMessageDelayed(0,5000);
                    break;
                case NetUntil.NET_GETDATA:
                    setJson(msg.obj.toString());
                    break;
            }
        }
    };



    EnvironmentalBean bean;
    private void setJson(String result){
        try {
            String serverinfo= new JSONObject(result).getString("serverinfo");
            JSONObject info= new JSONObject(serverinfo);
            Message message=handler.obtainMessage();
            if(info.isNull("Status")){
                bean=new EnvironmentalBean();
                bean.setLightIntensity(info.getInt("LightIntensity"));
                bean.setHumidity(info.getInt("humidity"));
                bean.setTemperature(info.getInt("temperature"));
                bean.setCo2(info.getInt("co2"));
                bean.setPm(info.getInt("pm2.5"));
                netUntil.getData("{\"RoadId\" : 1}","http://"+ip+":"+port+"/transportservice/type/jason/action/GetRoadStatus.do",handler);
            }else{
                bean.setStatus(info.getInt("Status"));
                message.what=1;
                message.obj=bean;
                handler.sendMessage(message);
                if(bean!=null){
                    beans.add(bean);
                    Intent intent=new Intent();
                    intent.setAction("com.example.environmental");
                    intent.putExtra("environmental_data",new Gson().toJson(bean));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
                Log.e("list",""+beans.size());
                calculationAvg();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void calculationAvg() {
        if(beans.size()==12){
            int pm=0;
            int co2=0;
            int LightIntensity=0;
            int humidity=0;
            int temperature=0;
            int Status=0;
            for(EnvironmentalBean bean:beans){
                pm+=bean.getPm();
                co2+=bean.getCo2();
                LightIntensity+=bean.getLightIntensity();
                humidity+=bean.getHumidity();
                temperature+=bean.getTemperature();
            }
            insertDB(pm, co2, LightIntensity, humidity, temperature, Status);
            beans.clear();
        }
    }

    private void insertDB(int pm, int co2, int lightIntensity, int humidity, int temperature, int status) {
        if(database.isOpen()){
            Cursor cursor= database.query(EnvironmentalDB.TABLENAME,null,null,null,null,null,null);
            Log.e("cursor",cursor.getCount()+"");
            if(cursor!=null && cursor.getCount()==5){
                cursor.moveToNext();
                String time=cursor.getString(6);
                Log.e("createData",time);
                database.delete(EnvironmentalDB.TABLENAME,"createDate=?",new String[]{time});
            }
            cursor.close();
            EnvironmentalBean environmentalBean=new EnvironmentalBean(pm/12,co2/12, lightIntensity /12,humidity/12,temperature/12, status /12);
            Long l=database.insert(EnvironmentalDB.TABLENAME,null,getContentValues(environmentalBean));
            Log.e("eee",l+"");
        }
    }

    private ContentValues getContentValues(EnvironmentalBean bean){
        ContentValues values=new ContentValues();
        values.put("pm",bean.getPm());
        values.put("co2",bean.getCo2());
        values.put("LightIntensity",bean.getLightIntensity());
        values.put("humidity",bean.getHumidity());
        values.put("temperature",bean.getTemperature());
        values.put("Status",bean.getStatus());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= simpleDateFormat.format(Calendar.getInstance().getTime());
        values.put("createDate",date);
        return values;
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if(database!=null){
            database.close();
        }
    }

}
