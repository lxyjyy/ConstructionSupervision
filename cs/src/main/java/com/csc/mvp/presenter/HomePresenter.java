package com.csc.mvp.presenter;

import android.app.Application;

import com.csc.mvp.contract.HomeContract;
import com.lxyjyy.cs.base.AppManager;
import com.lxyjyy.cs.di.scope.ActivityScope;
import com.lxyjyy.cs.mvp.BasePresenter;
import com.lxyjyy.cs.widget.imageloader.ImageLoader;

import com.lxyjyy.cs.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;


/**
 * Created by lxyjyy on 17/3/20.
 */

@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}