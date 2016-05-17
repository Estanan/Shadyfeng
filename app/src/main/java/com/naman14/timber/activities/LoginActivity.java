package com.naman14.timber.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naman14.timber.R;
import com.naman14.timber.utils.TimberUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by shadyfeng on 2016/5/13.
 * 登录
 */
public class LoginActivity extends BaseThemedActivity {
    Toolbar toolbar;
    TextView tv_username,tv_password;
    Button bt_login;
    BmobUser bu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        toolbar.setTitle("用户登录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bu = new BmobUser();
                bu.setUsername(tv_username.getText().toString());
                bu.setPassword(tv_password.getText().toString());
                bu.login(LoginActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        TimberUtils.showToast(LoginActivity.this, "登录成功");
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("username",tv_username.getText().toString());
                        startActivity(intent);
                        //通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
                        //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        TimberUtils.showToast(LoginActivity.this, "登录失败" + msg + code);
                    }
                });
            }
        });
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        bt_login= (Button) findViewById(R.id.bt_login);
        tv_username= (TextView) findViewById(R.id.et_username);
        tv_password= (TextView) findViewById(R.id.et_password);
    }

}
