package com.naman14.timber.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman14.timber.R;
import com.naman14.timber.TimberApp;
import com.naman14.timber.adapters.SongsListAdapter;
import com.naman14.timber.models.AllSongs;
import com.naman14.timber.models.Song;
import com.naman14.timber.models.TopSongLists;
import com.naman14.timber.utils.TimberUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by shadyfeng on 2016/5/19.
 * 个人信息歌单详情页
 */
public class DetailsActivity extends BaseThemedActivity {

    private ImageView ivPicture;
    private TextView tvIntroduce;
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private String username;
    private Song song;
    SongsListAdapter songsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TimberApp timberApp = (TimberApp) getApplicationContext();
        username = timberApp.getUsername();
        initView();
        initToolbar();
        Bundle bundle = this.getIntent().getExtras();
        String ways = bundle.getString("ways");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (ways != null) {
            switch (ways) {
                case "o":
                    //全部歌曲
                    ivPicture.setBackgroundResource(R.drawable.ic_launcher);
                    tvIntroduce.setText(R.string.allSongs);
                    BmobQuery<AllSongs> query = new BmobQuery<AllSongs>();

                    query.addWhereEqualTo("username", username);
                    query.findObjects(DetailsActivity.this, new FindListener<AllSongs>() {
                        @Override
                        public void onSuccess(List<AllSongs> list) {

                            if (list.size() != 0) {
                                for (AllSongs song : list) {
                                    List<Song> songs = list.get(0).getSongArr();
                                    String a = songs.get(0).getAlbumName();
                                    recyclerView.setAdapter(new SongsListAdapter(DetailsActivity.this, songs, true));
                                    TimberUtils.showToast(DetailsActivity.this, "获取列表成功");
                                }
                            }else {
                                TimberUtils.showToast(DetailsActivity.this, "列表为空");
                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    break;
                case "t":
                    ivPicture.setBackgroundResource(R.drawable.ic_launcher);
                    tvIntroduce.setText(R.string.playlist_top_tracks);
                    BmobQuery<TopSongLists> query3 = new BmobQuery<TopSongLists>();
                    query3.addWhereEqualTo("username", username);
                    query3.findObjects(DetailsActivity.this, new FindListener<TopSongLists>() {
                        @Override
                        public void onSuccess(List<TopSongLists> list) {
                            if (list.size() != 0) {
                                List<Song> songs = list.get(0).getPlayLists();
                                recyclerView.setAdapter(new SongsListAdapter(DetailsActivity.this, songs, true));
                                TimberUtils.showToast(DetailsActivity.this, "获取列表成功");
                            } else {
                                TimberUtils.showToast(DetailsActivity.this, "列表为空");
                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    break;
            }
        }
    }

    private void initView() {
        ivPicture = (ImageView) findViewById(R.id.iv_picture);
        tvIntroduce = (TextView) findViewById(R.id.tv_introduce);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsActivity.this.onBackPressed();
            }
        });
    }
}
