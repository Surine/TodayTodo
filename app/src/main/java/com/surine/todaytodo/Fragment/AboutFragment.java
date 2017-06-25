package com.surine.todaytodo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.surine.todaytodo.R;

/**
 * Created by surine on 2017/6/25.
 */

/**
 * Created by surine on 2017/6/3.
 */

public class AboutFragment extends Fragment{
    public static final String ARG = "Fragment";
    View v;
    public static AboutFragment getInstance(String title){
        AboutFragment fragment = new AboutFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG,title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("关于");
        v = inflater.inflate(R.layout.activity_about,container,false);
        initView();
        return v;
    }

    private void initView() {
    }
}