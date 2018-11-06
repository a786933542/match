package com.example.administrator.match.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.match.R;
import com.example.administrator.match.okhttp.okHTTP;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_lightdetection extends android.support.v4.app.Fragment implements View.OnClickListener{
    private Context context;
    public View view;
    private int down;
    private int up;
    private boolean b;

    public Fragment_lightdetection(){}
    private Button select;
    private TextView lightvalue,prompt;
    private Switch switchshow;

    Handler myhandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==6){
                String s = msg.obj.toString();
                //Log.e("lightdetection",""+s);
                getDate(s);

            }else if (msg.what==7){
                String s = msg.obj.toString();
                getSwicthShow(s);
            }
        }
    };

    public void getDate(String str){
        try {
            JSONObject jsonObject=new JSONObject(str);
            String serverinfo=jsonObject.getString("serverinfo");
            JSONObject object=new JSONObject(serverinfo);
            Log.e("lightdetection",""+serverinfo);
            int LightIntensity = object.getInt("LightIntensity");
            lightvalue.setText(""+LightIntensity);
           // Boolean is=switchshow.setChecked(true);
            if(LightIntensity>=up){
                prompt.setText("光照太强了，为你关闭路灯");
                if(!b){
                    Toast.makeText(getActivity(),"路灯以自动关闭",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"请手动关闭路灯",Toast.LENGTH_SHORT).show();
                }
            } else if(LightIntensity<=up&&LightIntensity>=down){
                prompt.setText("光照适宜");
            }else if(LightIntensity<=down&&LightIntensity>=0){
                prompt.setText("光照太弱了，为你打开路灯");
                if(!b){
                    Toast.makeText(getActivity(),"路灯以自动打开",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"请手动打开路灯",Toast.LENGTH_SHORT).show();
                }
            }
            Log.e("lightdetection",""+LightIntensity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getSwicthShow(String str){
        try {
            JSONObject jsonObject=new JSONObject(str);
            String serverinfo=jsonObject.getString("serverinfo");
            JSONObject object=new JSONObject(serverinfo);
            Log.e("down",""+serverinfo);
            down = object.getInt("Down");
            up = object.getInt("Up");
            Log.e("lightdetection",""+ down +""+ up);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_lightdetection,container,false);
        initview();
        switchselect();
        switchshow();
        return view;
    }
    public void initview(){
        select =view.findViewById(R.id.select);
        lightvalue=view.findViewById(R.id.selectvalue);
        prompt=view.findViewById(R.id.prompt);
        switchshow=view.findViewById(R.id.switchshow);
        select.setOnClickListener(this);
    }

    public void selectData(){
        String json ="{}";
        SharedPreferences cache = getActivity().getSharedPreferences("cache", MODE_PRIVATE);
        String ip = cache.getString("ip", null);
        String port = cache.getString("port", null);
        String CarInterface = "/transportservice/type/jason/action/ GetAllSense.do";
        int whats=6;
        okHTTP okHTTP = new okHTTP(json,ip,port,CarInterface,whats,myhandler);
        okHTTP.getConnection();
    }
    public void switchshow(){
        String json ="{}";
        SharedPreferences cache = getActivity().getSharedPreferences("cache", MODE_PRIVATE);
        String ip = cache.getString("ip", "192.168.1.115");
        String port = cache.getString("port", "8080");
        String CarInterface = "/transportservice/type/jason/action/GetLightSenseValve.do";
        int whats=7;
        okHTTP okHTTP = new okHTTP(json,ip,port,CarInterface,whats,myhandler);
        okHTTP.getConnection();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select:
                selectData();
                break;
        }
    }

    public void switchselect(){
        switchshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    /*手动*/
                    b = true;
                    //switchshow.setChecked(false);
                    //Toast.makeText(getActivity(),""+isChecked,Toast.LENGTH_SHORT).show();
                }else{
                    /*自动*/
                    b=false;
                }
            }
        });
    }



}
