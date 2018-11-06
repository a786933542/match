package com.example.administrator.match.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.match.R;
import com.example.administrator.match.dialog.DiaLogUntil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvNetSetting;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etRepassword;
    private EditText etPhone;
    private Button btnCancel;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvNetSetting = (TextView) findViewById(R.id.tv_netSetting);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRepassword = (EditText) findViewById(R.id.et_repassword);
        etPhone = (EditText) findViewById(R.id.et_phone);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnRegister = (Button) findViewById(R.id.btn_register);
        tvNetSetting=findViewById(R.id.tv_netSetting);
        setListener();
    }

    private void setListener(){
        tvNetSetting.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_netSetting:
                DiaLogUntil.showDiaLog(this);
                break;
            case R.id.btn_register:
                Boolean isOK=true;
                if(TextUtils.isEmpty(etUsername.getText())){
                    isOK=false;
                }
                if(TextUtils.isEmpty(etPassword.getText())){
                    isOK=false;
                }
                if(TextUtils.isEmpty(etRepassword.getText())){
                    isOK=false;
                }
                if(TextUtils.isEmpty(etPhone.getText())){
                    isOK=false;
                }

                if(isOK){
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancel:
                finish();
        }
    }
}
