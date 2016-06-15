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

package com.naman14.timber.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.naman14.timber.R;
import com.naman14.timber.TimberApp;
import com.naman14.timber.activities.BaseActivity;
import com.naman14.timber.adapters.SongsListAdapter;
import com.naman14.timber.dataloaders.SongLoader;
import com.naman14.timber.listeners.MusicStateListener;
import com.naman14.timber.models.AllSongs;
import com.naman14.timber.models.Song;
import com.naman14.timber.utils.PreferencesUtility;
import com.naman14.timber.utils.SortOrder;
import com.naman14.timber.utils.TimberUtils;
import com.naman14.timber.widgets.DividerItemDecoration;
import com.naman14.timber.widgets.FastScroller;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SongsFragment extends Fragment implements MusicStateListener {

    private SongsListAdapter mAdapter;
    private RecyclerView recyclerView;
    private PreferencesUtility mPreferences;
    private String username;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferencesUtility.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_recyclerview, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FastScroller fastScroller = (FastScroller) rootView.findViewById(R.id.fastscroller);
        fastScroller.setRecyclerView(recyclerView);

        new loadSongs().execute("");
        ((BaseActivity) getActivity()).setMusicStateListenerListener(this);

        return rootView;
    }

    public void restartLoader() {

    }

    public void onPlaylistChanged() {

    }

    public void onMetaChanged() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    private void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                List<Song> songList = SongLoader.getAllSongs(getActivity());
                mAdapter.updateDataSet(songList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.song_sort_by, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_by_az:
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_A_Z);
                reloadAdapter();
                return true;
            case R.id.menu_sort_by_za:
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_Z_A);
                reloadAdapter();
                return true;
            case R.id.menu_sort_by_artist:
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_ARTIST);
                reloadAdapter();
                return true;
            case R.id.menu_sort_by_album:
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_ALBUM);
                reloadAdapter();
                return true;
            case R.id.menu_sort_by_year:
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_YEAR);
                reloadAdapter();
                return true;
            case R.id.menu_sort_by_duration:
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_DURATION);
                reloadAdapter();
                return true;
            case R.id.action_uploadAll:
                uploadSongLists();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class loadSongs extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (getActivity() != null)
                mAdapter = new SongsListAdapter((AppCompatActivity) getActivity(), SongLoader.getAllSongs(getActivity()), false);
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            recyclerView.setAdapter(mAdapter);
            if (getActivity() != null)
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        }

        @Override
        protected void onPreExecute() {
        }
    }

    private void uploadSongLists() {
        final List<Song> arrayList = SongLoader.getAllSongs(getActivity());
        TimberApp timberApp = (TimberApp) getActivity().getApplicationContext();
        username = timberApp.getUsername();
        /**
         * 查询用户AllSongLists,获取SongLists的objectId
         */
        if (username == null) {
            TimberUtils.showToast(getActivity(), "请先登录");
        } else {
            final String[] objectId = new String[1];
            BmobQuery<AllSongs> query = new BmobQuery<>();
            query.addWhereEqualTo("username", username);
            query.findObjects(getActivity(), new FindListener<AllSongs>() {
                @Override
                public void onSuccess(List<AllSongs> list) {
                    for (AllSongs allSongs : list) {
                        objectId[0] = allSongs.getObjectId();
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });

            /**
             * 插入allsonglists
             */
            final AllSongs allsong = new AllSongs();
            allsong.setUsername(username);
            allsong.addAllUnique("songArr", arrayList);
            allsong.save(getActivity(), new SaveListener() {
                @Override
                public void onSuccess() {
                    TimberUtils.showToast(getActivity(), "allsong成功插入");
                }

                @Override
                public void onFailure(int i, String s) {

                    allsong.setSongArr(arrayList);
                    allsong.update(getActivity(), objectId[0], new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            TimberUtils.showToast(getActivity(), "allsong成功更新");
                            int s = arrayList.size();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            TimberUtils.showToast(getActivity(), "allsong更新失败" + s + i + objectId[0]);
                        }
                    });
                }
            });
        }
    }
}
