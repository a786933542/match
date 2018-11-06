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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.match.R;
import com.example.administrator.match.okhttp.okHTTP;
import com.example.administrator.match.until.NetUntil;

import org.json.JSONObject;

public class Fragment_trafficquery extends android.support.v4.app.Fragment {
    private Context context;
    private View view;
    private int i=1;
    private int one;

    public Fragment_trafficquery (){}
    private TextView oneroad,tworoad,threeroad;
    private Switch switchcar1,switchcar2,switchcar3;
    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==8){
                String s=msg.obj.toString();
                getJSONData(s);
            }
        }
    };

    public void getJSONData(String str){
        try {
            Log.e("Data",""+str);
            JSONObject jsonObject = new JSONObject(str);
            String serverinfo=jsonObject.getString("serverinfo");
            JSONObject objectinfo=new JSONObject(serverinfo);

            one = objectinfo.getInt("Status");

            Log.e("Status",""+ one);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.trafficquery,container,false);
        initview();
        myhandler.postDelayed(runnable,500);
        //getHTTP(json);

        return view;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int road1=0;
            int road2 = 0;
            int road3=0;
            String json="{\"RoadId\":"+i+"}";
            getHTTP(json);

            if (i>3){
                i=1;
            }
            if(i==1){
                road1=one;
            }else if(i==2){
                road2=one;
            }else if(i==3){
                road3=one;
                if(road1==1){
                    oneroad.setText("空荡");
                    //Toast.makeText(getActivity(),"顺畅",Toast.LENGTH_SHORT).show();
                }else if(one==2){
                    oneroad.setText("顺畅");
                }else if(one==3){
                    oneroad.setText("拥挤");
                }else if(one==4){
                    oneroad.setText("堵塞");
                } else if(one==5){
                    oneroad.setText("爆表");
                } else if(one==0){
                    oneroad.setText("未知");
                }
                if(road2==1){
                    tworoad.setText("空荡");
                    //Toast.makeText(getActivity(),"顺畅",Toast.LENGTH_SHORT).show();
                }else if(one==2){
                    tworoad.setText("顺畅");
                }else if(one==3){
                    tworoad.setText("拥挤");
                }else if(one==4){
                    tworoad.setText("堵塞");
                } else if(one==5){
                    tworoad.setText("爆表");
                } else if(one==0){
                    tworoad.setText("未知");
                }
                if(road3==1){
                    threeroad.setText("空荡");
                    //Toast.makeText(getActivity(),"顺畅",Toast.LENGTH_SHORT).show();
                }else if(one==2){
                    threeroad.setText("顺畅");
                }else if(one==3){
                    threeroad.setText("拥挤");
                }else if(one==4){
                    threeroad.setText("堵塞");
                } else if(one==5){
                    threeroad.setText("爆表");
                } else if(one==0){
                    threeroad.setText("未知");
                }
            }
            i+=1;
            myhandler.postDelayed(this,5000);
        }
    };
/*初始化*/
    public void initview(){
        oneroad=view.findViewById(R.id.oneroad);
        tworoad=view.findViewById(R.id.tworoad);
        threeroad=view.findViewById(R.id.threeroad);
        switchcar1=view.findViewById(R.id.switchcar1);
        switchcar2=view.findViewById(R.id.switchcar2);
        switchcar3=view.findViewById(R.id.switchcar3);
    }

    public void getHTTP(String json){
        /*SharedPreferences preferences=getActivity().getSharedPreferences("cache",Context.MODE_PRIVATE);
        String ip=preferences.getString("ip",null);
        String port = preferences.getString("port", null);
        String CarInterface = "/transportservice/type/jason/action/GetRoadStatus.do";
        String url = "http://"+ip+":"+port+CarInterface;*/

        SharedPreferences preferences=getActivity().getSharedPreferences("cache",Context.MODE_PRIVATE);
        String ip=preferences.getString("ip",null);
        String port = preferences.getString("port", null);
        String CarInterface = "/transportservice/type/jason/action/GetRoadStatus.do";
        int whats=8;
        okHTTP okHTTP = new okHTTP(json,ip,port,CarInterface,whats,myhandler);
        okHTTP.getConnection();
    }
}
