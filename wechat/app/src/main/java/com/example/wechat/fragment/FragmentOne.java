package com.example.wechat.fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.example.wechat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentOne extends Fragment {
    //
//    //准备图片
    private int[] pic = new int[]{
            R.drawable.c1,
            R.drawable.c5,
            R.drawable.c2,
            R.drawable.c4,
            R.drawable.c6,
            R.drawable.c7,
            R.drawable.c8,
    };
    //准备文字
    private String data[][] =new String[][]{
            {"腾讯新闻","小杨哥·:618又整活限时发售..." ,"18:30"},
            {"zqy","记得学java'","14:32"},
            {"网易加速器","法环DLC存档一键换，直通暗道","17:00"},
            {"文件传输助手","[图片]","21:39"},
            {"订阅号消息","坚果搜题:2024杜蕾斯高校招聘|校.." ,"9:00"},
            {"微信运动","[应用消息]","昨天"},
            {"微信支付","已支付￥15.00","昨天"}
    };
//    //定义一个map集合存放数据
    private List<Map<String,String>> list = new ArrayList<>();
    private ListView datalist;
    private SimpleAdapter simpleAdapter;//适配器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fourthLayout = inflater.inflate(R.layout.fragment_one, container, false);
                datalist =fourthLayout.findViewById(R.id.datalist);

        for(int i=0;i<data.length;i++){
            Map<String,String> map = new HashMap<>();
            map.put("pic",String.valueOf(pic[i]));
            map.put("title",data[i][0]);
            map.put("price",data[i][1]);
            map.put("newprice",data[i][2]);
            list.add(map);//将map放到list集合中
        }

        simpleAdapter = new SimpleAdapter(
                getActivity(),
                this.list,
                R.layout.data_list,
                new String[]{"pic","title","price","newprice"},
                new int[]{R.id.pic,R.id.title,R.id.price,R.id.newprice}
        );
        datalist.setAdapter(simpleAdapter);
        return fourthLayout;
    }
}