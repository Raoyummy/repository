package com.example.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends Activity {

        private Button btnLogin;
        private Button btnRegister;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);

            btnLogin = findViewById(R.id.btn_login);
            btnRegister = findViewById(R.id.btn_register);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 在这里编写跳转到注册页面的逻辑
                    Intent intent = new Intent(com.example.wechat.WelcomeActivity.this, com.example.wechat.LoginActivity.class);
                    startActivity(intent);
                }
            });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 在这里编写跳转到注册页面的逻辑
                    Intent intent = new Intent(com.example.wechat.WelcomeActivity.this, com.example.wechat.RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }

    }