package com.example.administrator.match.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.match.R;

public class dataAnalysisFragment extends Fragment{

    private ViewPager viewPager;
    private View view1;
    private View view2;
    private View view3;
    private View view4;
    private View view5;
    private View view6;

    

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_data_analysis,null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        view1 = (View) view.findViewById(R.id.view1);
        view2 = (View) view.findViewById(R.id.view2);
        view3 = (View) view.findViewById(R.id.view3);
        view4 = (View) view.findViewById(R.id.view4);
        view5 = (View) view.findViewById(R.id.view5);
        view6 = (View) view.findViewById(R.id.view6);
        return view;
    }
}
