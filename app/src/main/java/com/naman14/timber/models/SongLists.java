package com.naman14.timber.models;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by shadyfeng on 2016/5/20.
 *
 */
public class SongLists extends BmobObject{
    private String username;
    private List<Playlist> playLists;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Playlist> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(List<Playlist> playLists) {
        this.playLists = playLists;
    }
}
