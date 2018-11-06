package com.example.administrator.match.until;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUntil {


    public static void putString(Context context,String name,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("cache",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(name,value);
        editor.commit();
    }
    public static void putBoolean(Context context,String name,Boolean value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("cache",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(name,value);
        editor.commit();
    }

    /*public static void putCarSpeedOrAccount(Context context,String name,String value,String CarId,String CarIdValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences("cache",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(name,value);
        editor.commit();
    }*/

    public static String getString(Context context,String name,String defValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences("cache",Context.MODE_PRIVATE);
        return sharedPreferences.getString(name,defValue);
    }
    public static Boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences("cache",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,defValue);
    }

}
