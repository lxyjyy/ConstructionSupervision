package com.csc.di.componet;

import com.csc.common.AppComponent;
import com.csc.di.module.HomeModule;
import com.csc.mvp.ui.activity.HomeActivity;
import com.lxyjyy.cs.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by lxyjyy on 17/3/20.
 */

@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
}