package com.naman14.timber.models;

import cn.bmob.v3.BmobUser;

/**
 * Created by shadyfeng on 2016/5/12.
 */
public class Users extends BmobUser {
    private String username;
    private String password;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
