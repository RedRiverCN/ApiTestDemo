package com.red.apitestdemo.module.video.videoDetail.info;

import android.os.Bundle;
import android.widget.TextView;

import com.red.apitestdemo.R;
import com.red.apitestdemo.base.BaseFragment;
import com.red.apitestdemo.bean.VideoDetailsInfo;
import com.red.apitestdemo.utils.ConstantUtil;

import butterknife.BindView;

/**
 * Created by Red on 2017/7/6.
 */

public class VideoInfoFragment extends BaseFragment<VideoInfoPresenter, VideoInfoModel>
        implements VideoInfoContract.VideoInfoView {

    @BindView(R.id.tv_video_title)
    TextView mVideoTitle;
    @BindView(R.id.tv_subtitle1)
    TextView mSubtitle1;
    @BindView(R.id.tv_subtitle2)
    TextView mSubtitle2;
    @BindView(R.id.tv_subtitle3)
    TextView mSubtitle3;
    @BindView(R.id.tv_description1)
    TextView mDescription1;
    @BindView(R.id.tv_description2)
    TextView mDescription2;
    @BindView(R.id.tv_description3)
    TextView mDescription3;



    private int mId;
    private VideoDetailsInfo.DataBean mVideoDetailsInfo;

    public static VideoInfoFragment newInstance(int id) {
        VideoInfoFragment fragment = new VideoInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public int getLayoutResId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void finishCreateView(Bundle state) {
        mId = getArguments().getInt(ConstantUtil.EXTRA_ID);
        mPresenter.loadData();
    }



    private void finishTask() {
    }

    @Override
    public void loadData() {

    }

    @Override
    public void setVId(int id) {
        mId = id;
    }
    @Override
    public int getVId() {
        return mId;
    }
}
