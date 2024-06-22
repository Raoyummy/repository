package com.example.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wechat.dbutil.InternetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends Activity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private static final int LOGIN_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                System.out.println("用户名: " + username + "，密码是:" + password);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", username);
                    jsonObject.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                login("http://192.168.253.38:8080/GDGSWeChatService04/login", jsonObject.toString());
            }
        });
    }

    private void login(String url, String jsonStr) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = InternetUtil.post(url, jsonStr);
                    Message msg = Message.obtain();
                    msg.what = LOGIN_RESULT;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_RESULT:
                    System.out.println(msg.obj.toString());
                    handleLoginResult(msg.obj.toString());
                    break;
            }
        }
    };

    private void handleLoginResult(String response) {
        try {
            JSONObject responseJson = new JSONObject(response);
            Object data = responseJson.get("data");
            if (data instanceof JSONObject) {
                JSONObject dataObject = (JSONObject) data;
                if (dataObject.has("id")) {
                    // Login successful, navigate to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Close LoginActivity
                }
            } else if (data instanceof String && data.equals("0")) {
                // Login failed, show error message
                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            } else {
                // Unexpected response
                Toast.makeText(LoginActivity.this, "意外错误", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
        }
    }
}