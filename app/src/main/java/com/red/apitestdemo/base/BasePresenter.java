package com.red.apitestdemo.base;

import android.content.Context;

/**
 * Created by Red on 2017/6/17.
 */

public abstract class BasePresenter<V, M> {
    public Context mContext;
    public V mView;
    public M mModel;

    public void setViewAndModel(V view, M model) {
        this.mView = view;
        this.mModel = model;
    }
}