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
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.appthemeengine.ATE;
import com.naman14.timber.R;
import com.naman14.timber.TimberApp;
import com.naman14.timber.dataloaders.PlaylistLoader;
import com.naman14.timber.dialogs.CreatePlaylistDialog;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.SongLists;
import com.naman14.timber.subfragments.PlaylistPagerFragment;
import com.naman14.timber.utils.TimberUtils;
import com.naman14.timber.widgets.MultiViewPager;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;

public class PlaylistFragment extends Fragment {

    int playlistcount;
    FragmentStatePagerAdapter adapter;
    MultiViewPager pager;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_playlist, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.playlists);

        final List<Playlist> playlists = PlaylistLoader.getPlaylists(getActivity(), true);
        playlistcount = playlists.size();

        pager = (MultiViewPager) rootView.findViewById(R.id.playlistpager);

        adapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return playlistcount;
            }

            @Override
            public Fragment getItem(int position) {
                return PlaylistPagerFragment.newInstance(position);
            }

        };
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        new upload().execute("");;
        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("dark_theme", false)) {
            ATE.apply(this, "dark_theme");
        } else {
            ATE.apply(this, "light_theme");
        }
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_playlist, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_playlist:
                CreatePlaylistDialog.newInstance().show(getChildFragmentManager(), "CREATE_PLAYLIST");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updatePlaylists(final long id) {
        final List<Playlist> playlists = PlaylistLoader.getPlaylists(getActivity(), true);
        playlistcount = playlists.size();
        adapter.notifyDataSetChanged();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < playlists.size(); i++) {
                    long playlistid = playlists.get(i).id;
                    if (playlistid == id) {
                        pager.setCurrentItem(i);
                        break;
                    }
                }
            }
        }, 200);

    }

    private class upload extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            final List<Playlist> playlists = PlaylistLoader.getPlaylists(getActivity(), true);
            TimberApp timberApp= (TimberApp) getActivity().getApplicationContext();
            username=timberApp.getUsername();
            if (username!=null){
                SongLists songLists=new SongLists();
                songLists.setUsername(username);
                songLists.addAllUnique("playLists", playlists);
                songLists.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        TimberUtils.showToast(getActivity(), "成功插入");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        TimberUtils.showToast(getActivity(), "插入失败" + s);
                    }
                });
            }
            return "Executed";
        }
    }
}

