package com.example.administrator.match.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.match.R;
import com.example.administrator.match.activity.LoginActivity;
import com.example.administrator.match.until.CacheUntil;

import okhttp3.Cache;

public class DiaLogUntil {


    public static void showDiaLog(final Context context){
        final AlertDialog alertDialog=new AlertDialog.Builder(context).create();
        View view= View.inflate(context,R.layout.dialog_netsetting,null);
        final EditText ip= view.findViewById(R.id.ip);
        final EditText port= view.findViewById(R.id.port);
        Button yes= view.findViewById(R.id.yes);
        Button no= view.findViewById(R.id.no);
        ip.setText(CacheUntil.getString(context,"ip","192.168.1.101"));
        port.setText( CacheUntil.getString(context,"port","8080"));

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ip.getText()+"/"+port.getText(), Toast.LENGTH_SHORT).show();
                CacheUntil.putString(context,"ip",ip.getText()+"");
                CacheUntil.putString(context,"port",port.getText()+"");
                alertDialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        port.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus && !TextUtils.isEmpty(port.getText())){
                    int index=Integer.parseInt(port.getText()+"");
                    if(index>65535 || index<0){
                        Toast.makeText(context, "端口号超出范围", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setView(view);
        alertDialog.show();
    }

}
