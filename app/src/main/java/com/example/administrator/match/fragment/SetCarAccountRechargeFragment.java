package com.example.administrator.match.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.match.R;
import com.example.administrator.match.sqlite.CarRecordBean;
import com.example.administrator.match.sqlite.CarReplenishDBManager;
import com.example.administrator.match.okhttp.okHTTP;
import com.example.administrator.match.until.CacheUntil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class SetCarAccountRechargeFragment extends Fragment {

    private Button btn_select_money;
    private Spinner spinner_select;
    private TextView tv_car_money;
    private Spinner spinner_insert;
    private EditText et_car_money;
    private Button btn_insert_money;
    private String json;
    private String ip;
    private String portNum;
    private int money = 1;
    private String selectedItem = "1";
    private String insertItem = "1";
    private CarReplenishDBManager db;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2){
                AccountRecharge(msg.obj.toString());
            }else if (msg.what ==3){
                String s = msg.obj.toString();
                String replace = s.replace("{\"serverinfo\":\"{\\\"result\\\":\\\"", "");
                s = replace.replace("\\\"}\\n\"}", "");
                Toast.makeText(getActivity(), "充值成功"+s, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private int balance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_set_car_account_recharge, null);
            //获取数据库的ip地址和端口
            SharedPreferences share = getActivity().getSharedPreferences("cache", MODE_PRIVATE);
            ip = share.getString("ip", null);
            portNum = share.getString("port", null);

            //初始化布局
            initView(view);
            //
            db = new CarReplenishDBManager(getActivity());
            btn_select_money.setOnClickListener(new MyOnClickListener());
            btn_insert_money.setOnClickListener(new MyOnClickListener());
            //查询按钮监听
            spinner_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedItem = (String) spinner_select.getSelectedItem();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            //充值按钮监听
            spinner_insert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    insertItem = (String) spinner_insert.getSelectedItem();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });


        return view;
    }


    private void initView(View view) {
        btn_select_money = (Button) view.findViewById(R.id.btn_select_money);
        spinner_select = (Spinner) view.findViewById(R.id.spinner_select);
        tv_car_money = (TextView) view.findViewById(R.id.tv_car_money);
        spinner_insert = (Spinner) view.findViewById(R.id.spinner_insert);
        et_car_money = (EditText) view.findViewById(R.id.et_car_money);
        btn_insert_money = (Button) view.findViewById(R.id.btn_insert_money);
    }

    //查询小车账户余额
    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_select_money:
                    //获取服务器的json数据
                    getData();
                break;
                case R.id.btn_insert_money:
                    //添加到服务器
                    setData();
                    //保存充值记录到数据库
                    saveRecord();
                break;
            }
        }
    }

    //充值记录保存到数据库
    private void saveRecord() {
        ArrayList<CarRecordBean> carList = new ArrayList<>();
        CarRecordBean bean = new CarRecordBean();
        //获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date(System.currentTimeMillis());//获取当前系统时间
        String time = format.format(date);

        //获取当前用户
        String userName = CacheUntil.getString(getActivity(), "username", null);
        //String people = intent.getStringExtra("userName");
        bean.setCarId(insertItem);
        bean.setPeople(userName);
        bean.setMoney(money+"");
        bean.setTime(time);

        carList.add(bean);
        db.addRecord(carList);
    }

    private void getData() {
        String CarInterface = "/transportservice/type/jason/action/GetCarAccountBalance.do";
        json = "{\"CarId\" :"+selectedItem+" }";
        int msgWhat = 2;
        okHTTP okHTTP = new okHTTP(json,ip,portNum,CarInterface,msgWhat,handler);
        okHTTP.getConnection();
    }

    private void setData(){
        String CarInterface = "/transportservice/type/jason/action/SetCarAccountRecharge.do";
        money = Integer.valueOf(et_car_money.getText().toString());
        json = "{\"CarId\" : "+insertItem+" ,\"Money\" : "+money+"}";
        int msgWhat = 3;
        okHTTP okHTTP = new okHTTP(json,ip,portNum,CarInterface,msgWhat,handler);
        okHTTP.getConnection();
    }

    public void AccountRecharge(String result){
        try {
            String serverinfo = new JSONObject(result).getString("serverinfo");
            JSONObject info= new JSONObject(serverinfo);
            balance = info.getInt("Balance");
            tv_car_money.setText("账户余额："+balance+"元");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
