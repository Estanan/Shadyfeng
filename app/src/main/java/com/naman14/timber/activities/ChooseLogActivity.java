package com.naman14.timber.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naman14.timber.R;

/**
 * Created by shadyfeng on 2016/5/11.
 * 选择登陆方式
 */
public class ChooseLogActivity extends BaseThemedActivity implements View.OnClickListener {

    Button phone_log, regist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooselog);

        phone_log = (Button) findViewById(R.id.phone_log);
        regist = (Button) findViewById(R.id.regist);
        phone_log.setOnClickListener(this);
        regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_log:
                Toast.makeText(this,"手机号登录",Toast.LENGTH_LONG).show();
                Intent logIntent=new Intent(this,LoginActivity.class);
                startActivity(logIntent);
                break;
            case R.id.regist:
                Intent intent=new Intent(this,RegestActivity.class);
                startActivity(intent);
                Toast.makeText(this,"注册",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
