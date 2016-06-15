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
import android.widget.Button;

import com.naman14.timber.R;
import com.naman14.timber.TimberApp;
import com.naman14.timber.adapters.UserSongListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

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
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfor);

        initView();
        initData();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });


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
                collapsingToolbar.setTitle(username+ "de个人中心");
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut(UserInforActivity.this);   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(UserInforActivity.this); // 现在的currentUser是null了
                Intent intent = new Intent(UserInforActivity.this, LoginActivity.class);
                startActivity(intent);
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
                        bundle.putString("ways", "t");
                        Intent intentsee = new Intent(UserInforActivity.this, DetailsActivity.class);
                        intentsee.putExtras(bundle);
                        startActivity(intentsee);
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
        btn_logout= (Button) findViewById(R.id.bt_logout);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private List<String> datas;
    private void initData() {
        datas = new ArrayList<>();
//        BmobQuery<SongLists> query=new BmobQuery<SongLists>();
        TimberApp timberApp= (TimberApp) getApplicationContext();
        username=timberApp.getUsername();
        datas.add("全部歌曲");
//        datas.add("最近播放");
//        datas.add("最近添加");
        datas.add("我的最佳单曲");



//        }
//        for (int i=0;i<)
        userSongListAdapter=new UserSongListAdapter(UserInforActivity.this,datas);
        recyclerView.setAdapter(userSongListAdapter);
    }
}
