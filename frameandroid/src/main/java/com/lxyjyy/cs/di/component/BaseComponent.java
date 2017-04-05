package com.lxyjyy.cs.di.component;

import com.lxyjyy.cs.base.BaseApplication;
import com.lxyjyy.cs.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 */
@Singleton
@Component(modules={AppModule.class})
public interface BaseComponent {
    void inject(BaseApplication application);
}
