package com.naman14.timber.models;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by shadyfeng on 2016/5/23.
 */
public class RecentSongLists extends BmobObject{
    private String username;
    private List<Song> playLists;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Song> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(List<Song> playLists) {
        this.playLists = playLists;
    }
}
