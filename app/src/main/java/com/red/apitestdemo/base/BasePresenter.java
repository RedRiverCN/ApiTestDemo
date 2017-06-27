package com.red.apitestdemo.base;

import android.content.Context;

/**
 * Created by Red on 2017/6/17.
 */

public abstract class BasePresenter<T, E> {
    public Context mContext;
    public T mView;
    public E mModel;

    public void setViewAndModel(T view, E model) {
        this.mView = view;
        this.mModel = model;
    }
}