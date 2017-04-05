package com.csc.mvp.model;

import android.app.Application;

import com.csc.mvp.contract.HomeContract;
import com.csc.mvp.model.api.cache.CacheManager;
import com.csc.mvp.model.api.service.ServiceManager;
import com.google.gson.Gson;
import com.lxyjyy.cs.di.scope.ActivityScope;
import com.lxyjyy.cs.mvp.BaseModel;

import javax.inject.Inject;

import static com.lxyjyy.cs.utils.Preconditions.checkNotNull;

/**
 * Created by lxyjyy on 17/3/20.
 */

@ActivityScope
public class HomeModel extends BaseModel<ServiceManager, CacheManager> implements HomeContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public HomeModel(ServiceManager serviceManager, CacheManager cacheManager, Gson gson, Application application) {
        super(serviceManager, cacheManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}