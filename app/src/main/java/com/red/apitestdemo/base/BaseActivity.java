package com.red.apitestdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


import com.red.apitestdemo.utils.MVPUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Red on 2017/6/16.
 * Activity基类
 */

public abstract class BaseActivity<T extends BasePresenter,E extends BaseModel> extends FragmentActivity {

    public Context activity;
    public T mPresenter;
    public E mModel;
    //private List<SubscriberWrapper> subscribers = new LinkedList<>();

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());

        //获取Presenter和Model对象
        mPresenter= MVPUtil.getT(this,0);
        mModel= MVPUtil.getT(this,1);
        activity = this;

        if(null!=mPresenter){
            mPresenter.mContext=this;
        }
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //设置监听器
        initListener();
        //初始化mPresenter.setViewAndModel(this, mModel);
        initPresenter();
    }

    protected abstract void initPresenter();

    protected abstract void initListener();

    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initToolBar();
/*
    private class SubscriberWrapper {
        ResourceSubscriber subscriber;
        ActivityLifecycle unsubscribeOn;

        public SubscriberWrapper(ResourceSubscriber subscriber, ActivityLifecycle unsubscribeOn) {
            this.subscriber = subscriber;
            this.unsubscribeOn = unsubscribeOn;
        }
    }

    public void addSubscriber(ResourceSubscriber subscriber, ActivityLifecycle unsubscribeOn) {
        subscribers.add(new SubscriberWrapper(subscriber, unsubscribeOn));
    }

    @Override
    protected void onDestroy() {
        //取消订阅
        Iterator<SubscriberWrapper> it = subscribers.iterator();

        while (it.hasNext()) {
            SubscriberWrapper subscriberWrapper = it.next();
            if (subscriberWrapper.unsubscribeOn == ActivityLifecycle.OnDestroy) {
                Log.e("rxjava", "onDestroy==============>");
                subscriberWrapper.subscriber.dispose();
                it.remove();
            }
        }
        super.onDestroy();
    }*/
}
