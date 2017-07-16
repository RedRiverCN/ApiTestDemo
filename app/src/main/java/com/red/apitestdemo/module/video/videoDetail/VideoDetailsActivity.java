package com.red.apitestdemo.module.video.videoDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.red.apitestdemo.R;
import com.red.apitestdemo.base.BaseActivity;

import com.red.apitestdemo.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
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

    //region 控件及变量声明


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_downl)
    TextView tv_down;
    @BindView(R.id.tv_down1)
    TextView tv_down1;
    @BindView(R.id.tv_down2)
    TextView tv_down2;

    @BindView(R.id.jc_video_player)
    JCVideoPlayerStandard mVideoPlayer;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    SlidingTabLayout mSlidingTabLayout;


    private int mId;

    private List<Fragment> fragments = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.playVideo();
        //!FileDownloader.setup(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.viewOnDestroy();
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
    public void onSuccess(String videoURL, String title) {

        mVideoPlayer.setUp(videoURL, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
    }

    @Override
    public void onError(String error) {

        Toast.makeText(VideoDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void playImmediately() {
        mVideoPlayer.startButton.performClick();//模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频

    }

    @Override
    public ImageView getThumbViewHolder() {
        return mVideoPlayer.thumbImageView;
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.progressBar:
                mPresenter.startDownload();
                break;
        }
    }

    @Override
    public void setProgressBar(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public void setId(int id) {
        mId = id;
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    //region 实现基类的函数
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



    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initPresenter() {
        //Presenter绑定 ViewAndModel
        mPresenter.setViewAndModel(this, mModel);
    }
    //endregion

    //region 下载相关回调
    /*
    public void setFilenameTv(TextView filenameTv) {
        this.filenameTv = filenameTv;
    }

    private void updateSpeed(int speed) {
        speedTv.setText(String.format("%dKB/s", speed));
    }

    public void updateProgress(final int sofar, final int total, final int speed) {
        if (total == -1) {
            // chunked transfer encoding data
            pb.setIndeterminate(true);
        } else {
            pb.setMax(total);
            pb.setProgress(sofar);
        }

        updateSpeed(speed);

        if (detailTv != null) {
            detailTv.setText(String.format("sofar: %d total: %d", sofar, total));
        }
    }

    public void updatePending(BaseDownloadTask task) {
        if (filenameTv != null) {
            filenameTv.setText(task.getFilename());
        }
    }

    public void updatePaused(final int speed) {
        toast(String.format("paused %d", position));
        updateSpeed(speed);
        pb.setIndeterminate(false);
    }

    public void updateConnected(String etag, String filename) {
        if (filenameTv != null) {
            filenameTv.setText(filename);
        }
    }

    public void updateWarn() {
        toast(String.format("warn %d", position));
        pb.setIndeterminate(false);
    }

    public void updateError(final Throwable ex, final int speed) {
        toast(String.format("error %d %s", position, ex));
        updateSpeed(speed);
        pb.setIndeterminate(false);
        ex.printStackTrace();
    }

    public void updateCompleted(final BaseDownloadTask task) {

        toast(String.format("completed %d %s", position, task.getTargetFilePath()));

        if (detailTv != null) {
            detailTv.setText(String.format("sofar: %d total: %d",
                    task.getSmallFileSoFarBytes(), task.getSmallFileTotalBytes()));
        }

        updateSpeed(task.getSpeed());
        pb.setIndeterminate(false);
        pb.setMax(task.getSmallFileTotalBytes());
        pb.setProgress(task.getSmallFileSoFarBytes());
    }
*/

    public void toast(final String msg) {
            Snackbar.make(mVideoPlayer, msg, Snackbar.LENGTH_LONG).show();
    }
    //endregion
}
