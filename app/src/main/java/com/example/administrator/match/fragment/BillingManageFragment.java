package com.example.administrator.match.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.administrator.match.R;
import com.example.administrator.match.sqlite.CarRecordBean;
import com.example.administrator.match.sqlite.CarReplenishDBManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingManageFragment extends Fragment {

    private Spinner spinner;
    private Button btn_select;
    private TableLayout tl_record;
    private TextView tv_isRecord;
    private CarReplenishDBManager mgr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_billing_manage, null);
        //初始化
        initView(view);
        mgr = new CarReplenishDBManager(getActivity());
        //spinner的监听
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        btn_select.setOnClickListener(new MyOnClickListener());

        return view;

    }
    private void initView(View view) {
        spinner = view.findViewById(R.id.spinner);
        btn_select = view.findViewById(R.id.btn_select);
        tl_record = view.findViewById(R.id.tl_record);
        tv_isRecord = view.findViewById(R.id.tv_isRecord);

        tl_record.setVisibility(View.GONE);
        tv_isRecord.setVisibility(View.GONE);
        //record = findViewById(R.id.record);
    }
    private void sort(int item) {
        if (item==1){//降序

        }else {//默认升序

        }
    }
    class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            sort(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            ArrayList<Map<String, String>> query = query();
            if (query.size()>0){
                //有记录
                tl_record.setVisibility(View.VISIBLE);
                tv_isRecord.setVisibility(View.GONE);

                for (Map map : query) {
                    TableRow tr = new TableRow(getActivity());
                    tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
                    LinearLayout id_ll = new LinearLayout(getActivity());
                    id_ll.setWeightSum(1);
                    TextView id = new TextView(getActivity());
                    id.setText(map.get("id").toString());
                    id.setTextSize(20);
                    id.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    id_ll.addView(id);

                    LinearLayout carId_ll = new LinearLayout(getActivity());
                    carId_ll.setWeightSum(1);
                    TextView carId = new TextView(getActivity());
                    carId.setText(map.get("carId").toString());
                    carId.setTextSize(20);
                    carId.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    carId_ll.addView(carId);

                    LinearLayout money_ll = new LinearLayout(getActivity());
                    money_ll.setWeightSum(1);
                    TextView money = new TextView(getActivity());
                    money.setText(map.get("money").toString());
                    money.setTextSize(20);
                    money.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    money_ll.addView(money);

                    LinearLayout people_ll = new LinearLayout(getActivity());
                    people_ll.setWeightSum(1);
                    TextView people = new TextView(getActivity());
                    people.setText(map.get("people").toString());
                    people.setTextSize(20);
                    people.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    people_ll.addView(people);

                    LinearLayout time_ll = new LinearLayout(getActivity());
                    time_ll.setWeightSum(1);
                    TextView time = new TextView(getActivity());
                    time.setText(map.get("time").toString());
                    time.setTextSize(20);
                    time.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    time_ll.addView(time);
                    tr.addView(id_ll);
                    tr.addView(carId_ll);
                    tr.addView(money_ll);
                    tr.addView(people_ll);
                    tr.addView(time_ll);
                    tl_record.addView(tr,new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                }
            }else {
                //没有数据
                tl_record.setVisibility(View.GONE);
                tv_isRecord.setVisibility(View.VISIBLE);
            }
            /*Log.e("sss", query+"");
            //Map<String,String> map = new HashMap<>();
            for (Map map : query){
                Log.e("Id",map.get("id").toString());
                Log.e("carId",map.get("carId").toString());
                Log.e("money",map.get("money").toString());
                Log.e("people",map.get("people").toString());
                Log.e("time",map.get("time").toString());
            }*/
        }
    }

    //查询数据库的充值记录
    public ArrayList<Map<String,String>> query(){
        List<CarRecordBean> carBeans = mgr.queryRecord();
        ArrayList<Map<String,String>> carList = new ArrayList<>();
        for (CarRecordBean bean : carBeans){
            HashMap<String,String> map = new HashMap<>();
            map.put("id", bean.getId()+"");
            map.put("carId", bean.getCarId());
            map.put("money", bean.getMoney());
            map.put("people", bean.getPeople());
            map.put("time", bean.getTime());
            carList.add(map);
        }
        return carList;
    }


}
