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

import cn.bmob.v3.BmobObject;

public class Song extends BmobObject{

    public final long albumId;
    public final String albumName;
    public final long artistId;
    public final String artistName;
    public final int duration;
    public final long id;
    public final String title;
    public final int trackNumber;

    public long getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public long getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getDuration() {
        return duration;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public Song() {
        this.id = -1;
        this.albumId = -1;
        this.artistId = -1;
        this.title = "";
        this.artistName = "";
        this.albumName = "";
        this.duration = -1;
        this.trackNumber = -1;
    }

    public Song(long _id, long _albumId, long _artistId, String _title, String _artistName, String _albumName, int _duration, int _trackNumber) {
        this.id = _id;
        this.albumId = _albumId;
        this.artistId = _artistId;
        this.title = _title;
        this.artistName = _artistName;
        this.albumName = _albumName;
        this.duration = _duration;
        this.trackNumber = _trackNumber;
    }
}
