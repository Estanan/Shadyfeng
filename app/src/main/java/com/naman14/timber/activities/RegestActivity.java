package com.naman14.timber.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.naman14.timber.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by shadyfeng on 2016/5/12.
 */
public class RegestActivity extends BaseThemedActivity {

    Toolbar toolbar;
    TextView to_login;
    EditText et_username, et_password;
    Button regiest;
    ProgressBar login_progress;
    Thread thread;
    BmobUser bmobUser = new BmobUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        innitView();
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void innitView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        to_login = (TextView) findViewById(R.id.to_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        login_progress = (ProgressBar) findViewById(R.id.login_progress);
        regiest = (Button) findViewById(R.id.regist);
        //已有账号登录
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegestActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        regiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmobUser.setUsername(et_username.getText().toString());
                bmobUser.setPassword(et_password.getText().toString());
                login_progress.setVisibility(View.VISIBLE);
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bmobUser.signUp(RegestActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                showToast("注册成功");
//                        Intent intent=new Intent(RegestActivity.this,MainActivity.class);
//                        Bundle bundle=new Bundle();
//                        startActivity(intent);
                                Intent intent = new Intent();
                                intent.putExtra("username", et_username.getText().toString());
                                intent.setClass(RegestActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast(String.format("注册失败" + s + i));
                                if (i == 202) {
                                    showToast("老司机车牌已被抢先，换个车牌吧");
                                }
                                login_progress.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                thread.start();
            }
        });
    }

    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
