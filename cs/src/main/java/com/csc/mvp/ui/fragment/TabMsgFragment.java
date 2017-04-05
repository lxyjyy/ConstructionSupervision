package com.csc.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csc.R;
import com.csc.common.AppComponent;
import com.csc.common.WEFragment;
import com.csc.di.componet.DaggerTabMsgComponent;
import com.csc.di.module.TabMsgModule;
import com.csc.mvp.contract.TabMsgContract;
import com.csc.mvp.presenter.TabMsgPresenter;
import com.lxyjyy.cs.utils.UiUtils;

import static com.lxyjyy.cs.utils.Preconditions.checkNotNull;


/**
 * Created by lxyjyy on 17/3/21.
 */

public class TabMsgFragment extends WEFragment<TabMsgPresenter> implements TabMsgContract.View {


    public static TabMsgFragment newInstance() {
        TabMsgFragment fragment = new TabMsgFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerTabMsgComponent
                .builder()
                .appComponent(appComponent)
                .tabMsgModule(new TabMsgModule(this))//请将TabMsgModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.tab_msg_fragment, container, false);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

}