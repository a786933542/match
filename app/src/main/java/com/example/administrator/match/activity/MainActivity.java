package com.example.administrator.match.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.match.R;
import com.example.administrator.match.fragment.AccountPaymentSecurity;
import com.example.administrator.match.fragment.BillingManageFragment;
import com.example.administrator.match.fragment.BusInfoQuery;
import com.example.administrator.match.fragment.CarSpaceQuery;
import com.example.administrator.match.fragment.CarSpeed;
import com.example.administrator.match.fragment.CarSpeedAndAccountFragment;
import com.example.administrator.match.fragment.Fragment_busquery;
import com.example.administrator.match.fragment.Fragment_environment;
import com.example.administrator.match.fragment.Fragment_environment_histoty;
import com.example.administrator.match.fragment.Fragment_light_query;
import com.example.administrator.match.fragment.Fragment_light_setting;
import com.example.administrator.match.fragment.Fragment_lightdetection;
import com.example.administrator.match.fragment.Fragment_road;
import com.example.administrator.match.fragment.Fragment_road_state;
import com.example.administrator.match.fragment.Fragment_trafficlightmanagement;
import com.example.administrator.match.fragment.Fragment_trafficquery;
import com.example.administrator.match.fragment.Fragment_traveladvice;
import com.example.administrator.match.fragment.Fragment_vehiclerestrictions;
import com.example.administrator.match.fragment.PeopleCarControlAndManage;
import com.example.administrator.match.fragment.SetCarAccountRechargeFragment;
import com.example.administrator.match.fragment.TrafficRoadStatusQuery;
import com.example.administrator.match.services.EnvironmentalService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frame;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private List<Fragment>list;
    private ActionBarDrawerToggle toggle;
    private TextView title;
    private TextView netSetting;
    private Toolbar toolbar;
    private FragmentTransaction transaction;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 启动服务，实时更新数据
         */
        intent=new Intent(this, EnvironmentalService.class);
        startService(intent);

        fragmentManager=getSupportFragmentManager();
        findviews();

        initData();

        setListener();
    }

    private void findviews() {
        drawer=findViewById(R.id.drawer);
        frame = (FrameLayout) findViewById(R.id.frame);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        title=findViewById(R.id.title);
        netSetting=findViewById(R.id.tv_netSetting);
        toolbar=findViewById(R.id.toolbar);
    }

    private void setListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                transaction=fragmentManager.beginTransaction();
                Toast.makeText(MainActivity.this, ""+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                switch (menuItem.getItemId()){
                    case R.id.item1:
                        transaction.replace(R.id.frame,list.get(0));
                        break;
                    case R.id.item2:
                        transaction.replace(R.id.frame,list.get(1));
                        break;
                    case R.id.item3:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                    case R.id.item4:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                    case R.id.item5:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                    case R.id.item6:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                    case R.id.item7:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                    case R.id.item8:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                    case R.id.item9:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                    case R.id.item10:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                    case R.id.item11:
                        transaction.replace(R.id.frame,list.get(2));
                        break;
                }
                transaction.commit();
                drawer.closeDrawers();
                return true;
            }
        });
    }

    private void initData() {
        title.setText("");
        netSetting.setVisibility(View.GONE);

        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawer,toolbar,0,0);
        actionBarDrawerToggle.syncState();

        list=new ArrayList<>();

        //胡锟
        list.add(new BillingManageFragment());//账单管理
        list.add(new CarSpeedAndAccountFragment());//小车账户阈值
        list.add(new CarSpeed());
        list.add(new SetCarAccountRechargeFragment());
        list.add(new BusInfoQuery());
        list.add(new TrafficRoadStatusQuery());
        list.add(new CarSpaceQuery());
        list.add(new PeopleCarControlAndManage());
        list.add(new AccountPaymentSecurity());

        list.add(new Fragment_environment());//环境指标
        list.add(new Fragment_light_query());//红绿灯周期性查询
        list.add(new Fragment_environment_histoty());//环境历史记录

        //陈文斌
        list.add(new Fragment_road());//路灯设置
        list.add(new Fragment_road_state());//道路状态
        list.add(new Fragment_light_setting());//红绿灯设置
        list.add(new Fragment_trafficlightmanagement());//红绿灯管理

        //陈思燕
        list.add(new Fragment_lightdetection());//光照检测
        list.add(new Fragment_trafficquery());//路况查询
        list.add(new Fragment_traveladvice());//出行建议
        list.add(new Fragment_busquery());//公交查询
        list.add(new Fragment_vehiclerestrictions());//车辆限行
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

    private long time;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if(System.currentTimeMillis()-time<=3000){
                System.exit(0);
            }else{
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                time=System.currentTimeMillis();
            }
        }

        return true;
    }
}
