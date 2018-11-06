package com.example.administrator.match.fragment;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.match.R;
import com.example.administrator.match.adapter.EnvirAdapter;
import com.example.administrator.match.domain.EnvironmentalBean;
import com.example.administrator.match.until.CacheUntil;
import com.example.administrator.match.until.NetUntil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Fragment_environment extends Fragment{


    private TextView temp;
    private TextView humidity;
    private TextView light;
    private TextView co2;
    private TextView pm;
    private TextView status;
    private NetUntil netUntil;
    private GridView gridView;

    private final static int SEND_ENM=1;
    private final static int SEND_STATUS=2;

    private List<EnvironmentalBean> list=new ArrayList<>();
    private EnvirAdapter envirAdapter;


    private EnvironmentalBean bean=new EnvironmentalBean();

    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private BroadcastReceiver mReceiver;



    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_environment,null);

        gridView=view.findViewById(R.id.gridview);
        envirAdapter=new EnvirAdapter(getContext(),bean);
        gridView.setAdapter(envirAdapter);

        gridView.setOnItemClickListener(listener);


        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.environmental");
        broadcastManager= LocalBroadcastManager.getInstance(getActivity());
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data=intent.getStringExtra("environmental_data");
                Log.e("data",data);
                getData(data);
            }
        };
        broadcastManager.registerReceiver(mReceiver,intentFilter);

        return view;
    }

    private AdapterView.OnItemClickListener listener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    };

    private void getData(String data){
        EnvironmentalBean envir=new Gson().fromJson(data,EnvironmentalBean.class);
        bean.setStatus(envir.getStatus());
        bean.setPm(envir.getPm());
        bean.setCo2(envir.getCo2());
        bean.setTemperature(envir.getTemperature());
        bean.setHumidity(envir.getHumidity());
        bean.setLightIntensity(envir.getLightIntensity());
        envirAdapter.notifyDataSetChanged();
    }
}
