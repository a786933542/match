package com.example.administrator.match.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.match.R;
import com.example.administrator.match.until.CacheUntil;

public class CarSpeedAndAccountFragment extends android.support.v4.app.Fragment {

    private View view;
    private TextView tv_show_speed_and_account;
    private Button btn_select_speed_or_account;
    private TextView tv_show_speed_or_account;
    private Spinner spinner_car;
    private EditText et_max_speed_account;
    private EditText et_min_speed_account;
    private Button btn_insert_speed_account;
    private Button btn_account;
    private Button btn_speed;
    private boolean isSpeedOrAccount = true;//true表示设置速度,false表示设置账户
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_car_speed_and_account, null);
        initView();

        btn_account.setOnClickListener(new MyOnClickListener());
        btn_speed.setOnClickListener(new MyOnClickListener());

        btn_insert_speed_account.setOnClickListener(new MyOnClickListener());
        btn_select_speed_or_account.setOnClickListener(new MyOnClickListener());

        return view;
    }

    private void initView() {
        tv_show_speed_and_account = (TextView) view.findViewById(R.id.tv_show_speed_and_account);
        btn_select_speed_or_account = (Button) view.findViewById(R.id.btn_select_speed_or_account);
        tv_show_speed_or_account = (TextView) view.findViewById(R.id.tv_show_speed_or_account);
        spinner_car = (Spinner) view.findViewById(R.id.spinner_car);
        et_max_speed_account = (EditText) view.findViewById(R.id.et_max_speed_account);
        et_min_speed_account = (EditText) view.findViewById(R.id.et_min_speed_account);
        btn_insert_speed_account = (Button) view.findViewById(R.id.btn_insert_speed_account);
        btn_account = (Button) view.findViewById(R.id.btn_account);
        btn_speed = (Button) view.findViewById(R.id.btn_speed);
    }
    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_speed:
                    //设置文本
                    tv_show_speed_and_account.setText("速度");
                    tv_show_speed_or_account.setText("阀值：最低20km/h--最高60km/h");
                    et_max_speed_account.setHint("km/h");
                    et_min_speed_account.setHint("km/h");
                    btn_insert_speed_account.setText("设置速度阀值");
                    btn_account.setBackgroundResource(R.color.blue_700);
                    btn_speed.setBackgroundResource(R.color.gavy);

                    isSpeedOrAccount = true;
                break;
                case R.id.btn_account:
                    //设置文本
                    tv_show_speed_and_account.setText("账户");
                    tv_show_speed_or_account.setText("阀值：最低10元--最高500元");
                    et_max_speed_account.setHint("(元)");
                    et_min_speed_account.setHint("(元)");
                    btn_insert_speed_account.setText("设置账户阀值");
                    btn_speed.setBackgroundResource(R.color.blue_700);
                    btn_account.setBackgroundResource(R.color.gavy);

                    isSpeedOrAccount = false;
                break;
                case R.id.btn_insert_speed_account:
                    //判断输入框是否有值
                    if (!TextUtils.isEmpty(et_max_speed_account.getText().toString()) && !TextUtils.isEmpty(et_min_speed_account.getText().toString())){
                        String max = et_max_speed_account.getText().toString();
                        String min = et_min_speed_account.getText().toString();
                        //判断当前设置是账户还是速度
                        if (isSpeedOrAccount){
                            //速度的设置
                            CacheUntil.putString(view.getContext(),spinner_car.getSelectedItem()+"maxSpeed",max);
                            CacheUntil.putString(view.getContext(),spinner_car.getSelectedItem()+"minSpeed",min);
                            Toast.makeText(view.getContext(), "设置成功", Toast.LENGTH_SHORT).show();
                        }else {
                            //账户的设置
                            CacheUntil.putString(view.getContext(),spinner_car.getSelectedItem()+"maxAccount",max);
                            CacheUntil.putString(view.getContext(),spinner_car.getSelectedItem()+"minAccount",min);
                            Toast.makeText(view.getContext(), "设置成功", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(view.getContext(), "输入框的值不能为空", Toast.LENGTH_SHORT).show();
                    }
                break;

                case R.id.btn_select_speed_or_account:
                    if (isSpeedOrAccount) {
                        String maxSpeed = CacheUntil.getString(view.getContext(), spinner_car.getSelectedItem() + "maxSpeed", null);
                        String minSpeed = CacheUntil.getString(view.getContext(), spinner_car.getSelectedItem() + "minSpeed", null);
                        tv_show_speed_or_account.setText("阀值：最低"+minSpeed+"km/h--最高"+maxSpeed+"km/h");
                    }else {
                        String maxAccount = CacheUntil.getString(view.getContext(), spinner_car.getSelectedItem() + "maxAccount", null);
                        String minAccount = CacheUntil.getString(view.getContext(), spinner_car.getSelectedItem() + "minAccount", null);
                        tv_show_speed_or_account.setText("阀值：最低"+maxAccount+"元--最高"+minAccount+"元");
                    }
                break;
            }
        }
    }

}
