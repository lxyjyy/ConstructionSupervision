package com.csc.common;

import android.app.Application;

import com.csc.di.module.CacheModule;
import com.csc.di.module.ServiceModule;
import com.csc.mvp.model.api.cache.CacheManager;
import com.csc.mvp.model.api.service.ServiceManager;
import com.google.gson.Gson;
import com.lxyjyy.cs.base.AppManager;
import com.lxyjyy.cs.di.module.AppModule;
import com.lxyjyy.cs.di.module.ClientModule;
import com.lxyjyy.cs.di.module.GlobeConfigModule;
import com.lxyjyy.cs.di.module.ImageModule;
import com.lxyjyy.cs.widget.imageloader.ImageLoader;
import com.lxyjyy.cs.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by jess on 8/4/16.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ServiceModule.class, ImageModule.class,
        CacheModule.class, GlobeConfigModule.class})
public interface AppComponent {
    Application Application();

    //服务管理器,retrofitApi
    ServiceManager serviceManager();

    //缓存管理器
    CacheManager cacheManager();

    //Rxjava错误处理管理类
    RxErrorHandler rxErrorHandler();


    OkHttpClient okHttpClient();

    //图片管理器,用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    ImageLoader imageLoader();

    //gson
    Gson gson();

    //用于管理所有activity
    AppManager appManager();
}
