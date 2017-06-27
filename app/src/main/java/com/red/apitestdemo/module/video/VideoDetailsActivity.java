package com.red.apitestdemo.module.video;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.red.apitestdemo.R;
import com.red.apitestdemo.base.BaseActivity;
import com.red.apitestdemo.bean.VideoBean;
import com.red.apitestdemo.event.AppBarStateChangeEvent;
import com.red.apitestdemo.network.auxiliary.ApiConstants;
import com.red.apitestdemo.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Red on 2017/6/17.
 * 视频详情页
 */

public class VideoDetailsActivity extends BaseActivity<VideoPresenter,VideoModel>
        implements android.view.View.OnClickListener, VideoContract.View {


    public static Intent newIntent(Context packageContext, int id) {
        Intent i = new Intent(packageContext, VideoDetailsActivity.class);
        i.putExtra(ConstantUtil.EXTRA_ID, id);
        return i;
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mVideoPlayer;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.tv_player)
    TextView mTvPlayer;

    @BindView(R.id.tv_title)
    TextView mTitleText;


    private int mId;

    private List<Fragment> fragments = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public void setId(int id) {
        mId = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.playVideo();
    }

    @Override
    public void onLoading() {

        Toast.makeText(VideoDetailsActivity.this, "onLoading()发起Json请求", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish() {

        Toast.makeText(VideoDetailsActivity.this, "onFinish()请求完毕", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(VideoBean videoBean) {

        mTitleText.setText(videoBean.getTitle());
        mVideoPlayer.setUp(ApiConstants.VIDEO_BASE_URL + "demo/" +videoBean.getMp4url(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
    }

    @Override
    public void onError(String error) {

        Toast.makeText(VideoDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImgLoad(Bitmap bitmap) {
        //设置视频缩略图
        mVideoPlayer.thumbImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.tv_player:
                    mPresenter.playImmediately();
                break;
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    @Override
    protected void initListener() {
        mTvPlayer.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {

        return R.layout.activity_video_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getIntExtra(ConstantUtil.EXTRA_ID, 0);

            Toast.makeText(VideoDetailsActivity.this, "id = "+mId, Toast.LENGTH_SHORT).show();

        }

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeEvent() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {

                if (state == State.EXPANDED) {
                    //展开状态
                    mTvPlayer.setVisibility(View.GONE);
                    mTitleText.setVisibility(View.VISIBLE);
                    mToolbar.setContentInsetsRelative((int)(15 * VideoDetailsActivity.this.getResources().getDisplayMetrics().density + 0.5f), 0);//屏幕像素转换
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mTvPlayer.setVisibility(View.VISIBLE);
                    mTitleText.setVisibility(View.GONE);
                    mToolbar.setContentInsetsRelative(0, 0);
                } else {
                    mTvPlayer.setVisibility(View.GONE);
                    mTitleText.setVisibility(View.VISIBLE);
                    mToolbar.setContentInsetsRelative((int)(15 * VideoDetailsActivity.this.getResources().getDisplayMetrics().density + 0.5f), 0);
                }
            }
        });

    }


    @Override
    public void initToolBar() {

    }
}
