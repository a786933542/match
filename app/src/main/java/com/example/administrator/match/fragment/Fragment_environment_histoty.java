package com.example.administrator.match.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.administrator.match.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_environment_histoty extends android.support.v4.app.Fragment implements View.OnClickListener{

    private Spinner type,time;
    private Button query;
    private List<String>types;
    private List<String>times;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.environment_histoty,null);

        type=view.findViewById(R.id.type);
        time=view.findViewById(R.id.time);
        query=view.findViewById(R.id.query);


        types=new ArrayList<>();
        times=new ArrayList<>();
        times.add("3/m");
        times.add("5/m");

        types.add("空气温度");
        types.add("空气湿度");
        types.add("光照");
        types.add("CO2");
        types.add("PM2.5");
        types.add("道路状态");


        type.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,types));
        time.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,times));
        type.setSelection(0,true);
        time.setSelection(1,true);

        query.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        type.getSelectedItemPosition();
    }
}
