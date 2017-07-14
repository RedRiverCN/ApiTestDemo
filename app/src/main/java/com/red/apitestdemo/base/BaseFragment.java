package com.red.apitestdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.red.apitestdemo.utils.MVPUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Red on 2017/7/6.
 */

public abstract class BaseFragment <T extends BasePresenter,M extends BaseModel> extends Fragment{
    public Context activity;
    public T mPresenter;
    public M mModel;

    private Unbinder bind;

    public View parentView;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        //内部获取第一个类型参数的真实类型  ，反射new出对象
        mPresenter = MVPUtil.getT(this,0);
        //内部获取第二个类型参数的真实类型  ，反射new出对象
        mModel = MVPUtil.getT(this,1);
        activity = super.getActivity();
        //使得P层绑定M层和V层 mPresenter.setViewAndModel(this, mModel);
        initPresenter();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);

        finishCreateView(savedInstanceState);
        //设置监听器
        initListener();
    }

    protected abstract void initPresenter();

    protected abstract void initListener();

    protected abstract void finishCreateView(Bundle savedInstanceState);

    protected abstract int getLayoutResId();
}
