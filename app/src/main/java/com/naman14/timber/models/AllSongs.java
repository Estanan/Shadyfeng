/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.naman14.timber.models;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class AllSongs extends BmobObject{
    private String username;
    private List<Song> songArr;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Song> getSongArr() {
        return songArr;
    }

    public void setSongArr(List<Song> songArr) {
        this.songArr = songArr;
    }
}
