package com.example.wechat.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wechat.R;


public class FragmentThree extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fourthLayout = inflater.inflate(R.layout.fragment_three, container, false);
        return fourthLayout;
    }
}