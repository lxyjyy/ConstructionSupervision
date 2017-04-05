package com.csc.di.componet;

import com.csc.common.AppComponent;
import com.csc.di.module.TabMsgModule;
import com.csc.mvp.ui.fragment.TabMsgFragment;
import com.lxyjyy.cs.di.scope.ActivityScope;

import dagger.Component;


/**
 * Created by lxyjyy on 17/3/21.
 */

@ActivityScope
@Component(modules = TabMsgModule.class, dependencies = AppComponent.class)
public interface TabMsgComponent {
    void inject(TabMsgFragment tabMsgFragment);
}