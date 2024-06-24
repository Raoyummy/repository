package com.example.wechat.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wechat.MainActivity;
import com.example.wechat.OkManager;
import com.example.wechat.R;

/**
 * Created by user on 2020/6/11.
 */

public class FragmentFour extends Fragment {
    private ImageView testImageView;
    private final static String TAG = MainActivity.class.getSimpleName();

    private OkManager manager;
    private String img_path = "http://192.168.253.38:8080/GDGSWeChatService04/imgs/touxiang.png";  //我用来测试的

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fourthLayout = inflater.inflate(R.layout.fragment_four, container, false);
        testImageView = fourthLayout.findViewById(R.id.testImageView);
        manager = OkManager.getInstance();

        // 自动刷新显示头像图片
        loadProfileImage();

        return fourthLayout;
    }

    private void loadProfileImage() {
        manager.asyncDownLoadImgtByUrl(img_path, new OkManager.Fun3() {
            @Override
            public void onResponse(Bitmap bitmap) {
                if (bitmap != null) {
                    testImageView.setImageBitmap(bitmap);
                    Log.i(TAG, "Image loaded successfully");
                } else {
                    Log.e(TAG, "Failed to decode Bitmap from response");
                }
            }
        });
    }
}
