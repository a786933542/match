package com.example.administrator.match.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.match.R;
import com.example.administrator.match.dialog.DiaLogUntil;
import com.example.administrator.match.until.CacheUntil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView tv_netSetting;
    private EditText et_username;
    private EditText et_password;
    private CheckBox ck_remember_password;
    private CheckBox ck_auto_login;
    private Button btn_login;
    private Button btn_register;

    public static final String KEY_REMEMBER_PASSWORD="rememberPassword";
    public static final String KEY_AUTO_LOGIN="autoLogin";
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setListener();
    }

    private void setListener() {
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_netSetting.setOnClickListener(this);
    }

    private void findViews() {
        tv_netSetting = (TextView) findViewById(R.id.tv_netSetting);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        ck_remember_password = (CheckBox) findViewById(R.id.ck_remember_password);
        ck_auto_login = (CheckBox) findViewById(R.id.ck_auto_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        initData();
    }

    private void initData() {
       if(CacheUntil.getBoolean(this,KEY_AUTO_LOGIN,false)){
           Intent intent=new Intent(this,MainActivity.class);
           startActivity(intent);
           finish();
       }
        if(CacheUntil.getBoolean(this,KEY_REMEMBER_PASSWORD,false)){
            et_username.setText(CacheUntil.getString(this,KEY_USERNAME,""));
            et_password.setText(CacheUntil.getString(this,KEY_PASSWORD,""));
            ck_remember_password.setChecked(true);
       }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String username=et_username.getText()+"";
                String password=et_password.getText()+"";
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }else if((username.equals("admin1") || username.equals("admin2")) && password.equals("123456") ){
                        Intent intent=new Intent(this,MainActivity.class);
                        startActivity(intent);
                        cache();
                        finish();
                }else{
                    Toast.makeText(this, "用户名密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_register:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_netSetting:
                DiaLogUntil.showDiaLog(this);
                break;
        }
    }

    private void cache() {
        if(ck_remember_password.isChecked()){
            CacheUntil.putString(this,KEY_USERNAME,et_username.getText()+"");
            CacheUntil.putString(this,KEY_PASSWORD,et_password.getText()+"");
            CacheUntil.putBoolean(this,KEY_REMEMBER_PASSWORD,true);
        }else{
            CacheUntil.putBoolean(this,KEY_REMEMBER_PASSWORD,false);
        }
        if(ck_auto_login.isChecked()){
            CacheUntil.putBoolean(this,KEY_AUTO_LOGIN,true);
        }else{
            CacheUntil.putBoolean(this,KEY_AUTO_LOGIN,false);
        }
    }
}
