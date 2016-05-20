package com.naman14.timber.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.naman14.timber.R;
import com.naman14.timber.TimberApp;
import com.naman14.timber.adapters.UserSongListAdapter;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.SongLists;
import com.naman14.timber.utils.TimberUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by shadyfeng on 2016/5/16.
 * 个人信息页
 */
public class UserInforActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CollapsingToolbarLayout collapsingToolbar;
    BmobUser bmobUser=new BmobUser();
    UserSongListAdapter userSongListAdapter;
    String username;
    Bundle bundle=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfor);
        initView();
        initData();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TimberApp timberApp= (TimberApp) getApplicationContext();
        username=timberApp.getUsername();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        collapsingToolbar.setTitle(getString(R.string.userInfor));
//        collapsingToolbar.setExpandedTitleColor(getColor(R.color.colorPrimaryBlack));
//        collapsingToolbar.setCollapsedTitleTextColor(getColor(R.color.colorAccentBlack));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.userbackgroud);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(final Palette palette) {
                int defaultColor = getResources().getColor(R.color.colorAccentLightDefault);
                int defaultTitleColor = getResources().getColor(R.color.colorAccentBlack);
                int bgColor = palette.getDarkVibrantColor(defaultColor);
                int titleColor = palette.getLightVibrantColor(defaultTitleColor);
//                collapsingToolbar.setContentScrimColor(bgColor);
                collapsingToolbar.setCollapsedTitleTextColor(titleColor);
                collapsingToolbar.setExpandedTitleColor(titleColor);
                collapsingToolbar.setTitle(username + "de个人中心");
            }
        });

        userSongListAdapter.setOnItemClickLitener(new UserSongListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        bundle.putString("ways", "o");
                        Intent intent = new Intent(UserInforActivity.this, DetailsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        bundle.putString("ways", "see");
                        Intent intentsee = new Intent(UserInforActivity.this, DetailsActivity.class);
                        intentsee.putExtras(bundle);
                        startActivity(intentsee);
                        break;
                    case 2:
                        bundle.putString("ways", "w");
                        Intent intentw = new Intent(UserInforActivity.this, DetailsActivity.class);
                        intentw.putExtras(bundle);
                        startActivity(intentw);
                        break;
                    case 3:
                        bundle.putString("ways", "t");
                        Intent intentt = new Intent(UserInforActivity.this, DetailsActivity.class);
                        intentt.putExtras(bundle);
                        startActivity(intentt);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initView() {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapsing_toolbar);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        BmobQuery<SongLists> query=new BmobQuery<SongLists>();
        TimberApp timberApp= (TimberApp) getApplicationContext();
        username=timberApp.getUsername();
        query.addWhereEqualTo("username", "shady");

        query.findObjects(UserInforActivity.this, new FindListener<SongLists>() {
            @Override
            public void onSuccess(List<SongLists> list) {
                for (SongLists song : list) {
                   List<Playlist> all=list.get(0).getPlayLists();
                    List<String> data=new ArrayList<String>();
                    String s;
                    for (int i=0;i<all.size();i++){

                        s=all.get(i).getName();
                        data.add(s);
                    }
                    userSongListAdapter=new UserSongListAdapter(UserInforActivity.this,data);
                    recyclerView.setAdapter(userSongListAdapter);
//                    TimberUtils.showToast(UserInforActivity.this, s);

                }
                TimberUtils.showToast(UserInforActivity.this, "成功");
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initData() {
        List<String> datas = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            datas.add(i);
//        }
//        for (int i=0;i<)
        userSongListAdapter=new UserSongListAdapter(this,datas);
        recyclerView.setAdapter(userSongListAdapter);
    }

}
