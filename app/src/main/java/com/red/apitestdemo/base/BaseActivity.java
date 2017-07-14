package com.red.apitestdemo.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.red.apitestdemo.R;
import com.red.apitestdemo.utils.MVPUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Red on 2017/6/16.
 * Activity基类
 */

public abstract class BaseActivity<T extends BasePresenter,M extends BaseModel> extends AppCompatActivity {

    public Context activity;
    public T mPresenter;
    public M mModel;

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());

        //内部获取第一个类型参数的真实类型  ，反射new出对象
        mPresenter= MVPUtil.getT(this,0);
        //内部获取第二个类型参数的真实类型  ，反射new出对象
        mModel= MVPUtil.getT(this,1);
        activity = this;

        if(null!=mPresenter){ mPresenter.mContext=this; }

        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //透明状态栏
        ImmersionBar.with(this).init();
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //设置监听器
        initListener();
        //使得P层绑定M层和V层 mPresenter.setViewAndModel(this, mModel);
        initPresenter();
    }

    //region 要求实现的函数
    protected abstract int getLayoutId();
    protected abstract void initViews(Bundle savedInstanceState);
    protected abstract void initToolBar();
    protected abstract void initListener();
    protected abstract void initPresenter();
    //endregion

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();  //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
