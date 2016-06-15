package com.naman14.timber.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naman14.timber.R;
import com.naman14.timber.TimberApp;
import com.naman14.timber.utils.TimberUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by shadyfeng on 2016/5/13.
 * 登录
 */
public class LoginActivity extends BaseThemedActivity {
    Toolbar toolbar;
    TextView tv_username,tv_password,regist;
    Button bt_login;
    ProgressBar login_progress;
    BmobUser bu;
    Thread thread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        toolbar.setTitle("用户登录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegestActivity.class);
                startActivity(intent);
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bu = new BmobUser();
                TimberApp timberApp= (TimberApp) getApplicationContext();
                timberApp.setUsername(tv_username.getText().toString());
                bu.setUsername(tv_username.getText().toString());
                bu.setPassword(tv_password.getText().toString());
                login_progress.setVisibility(View.VISIBLE);
                thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bu.login(LoginActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                TimberUtils.showToast(LoginActivity.this, "登录成功");
                                login_progress.setVisibility(View.GONE);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("username", tv_username.getText().toString());
                                startActivity(intent);
                            }
                            @Override
                            public void onFailure(int code, String msg) {
                                TimberUtils.showToast(LoginActivity.this, "登录失败" + msg + code);
                                login_progress.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                thread.start();
            }
        });
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        bt_login= (Button) findViewById(R.id.bt_login);
        tv_username= (TextView) findViewById(R.id.et_username);
        tv_password= (TextView) findViewById(R.id.et_password);
        login_progress= (ProgressBar) findViewById(R.id.login_progress);
        regist= (TextView) findViewById(R.id.regist);
    }

}
