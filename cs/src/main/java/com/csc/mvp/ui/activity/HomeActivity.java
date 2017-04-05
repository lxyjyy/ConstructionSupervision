package com.csc.mvp.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.csc.R;
import com.csc.common.AppComponent;
import com.csc.common.DaggerAppComponent;
import com.csc.common.WEActivity;
import com.csc.di.componet.DaggerHomeComponent;
import com.csc.di.module.HomeModule;
import com.csc.mvp.contract.HomeContract;
import com.csc.mvp.presenter.HomePresenter;
import com.csc.mvp.ui.fragment.TabContactFragment;
import com.csc.mvp.ui.fragment.TabMineFragment;
import com.csc.mvp.ui.fragment.TabMsgFragment;
import com.csc.mvp.ui.fragment.TabWorkFragment;
import com.csc.service.OfflineMessageService;
import com.lxyjyy.cs.utils.UiUtils;


import butterknife.BindView;
import butterknife.OnClick;

import static com.lxyjyy.cs.utils.Preconditions.checkNotNull;

/**
 * 首页界面
 */

public class HomeActivity extends WEActivity<HomePresenter> implements HomeContract.View {

    private TabMsgFragment tabMsgFragment;
    private TabWorkFragment tabWorkFragment;
    private TabContactFragment tabContactFragment;
    private TabMineFragment tabMineFragment;

    @BindView(R.id.id_tab_bottom_msg)
    private LinearLayout mTabBtnMsg;
    @BindView(R.id.id_tab_bottom_work)
    private LinearLayout mTabBtnWork;
    @BindView(R.id.id_tab_bottom_contact)
    private LinearLayout mTabBtnContact;
    @BindView(R.id.id_tab_bottom_mine)
    private LinearLayout mTabBtnMine;
    @BindView(R.id.btn_tab_bottom_msg)
    private ImageButton btnMsg;
    @BindView(R.id.btn_tab_bottom_work)
    private ImageButton btnWork;
    @BindView(R.id.btn_tab_bottom_contact)
    private ImageButton btnContact;
    @BindView(R.id.btn_tab_bottom_mine)
    private ImageButton btnMine;

    private FragmentManager fragmentManager;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
    }

    @Override
    protected void initData() {
        fragmentManager = getSupportFragmentManager();

        //默认加载消息界面
        setTabSelection(0);
        //
        initService();

    }

    @OnClick({R.id.id_tab_bottom_msg,R.id.id_tab_bottom_work,R.id.id_tab_bottom_contact,R.id.id_tab_bottom_mine})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.id_tab_bottom_msg:
                setTabSelection(0);
                break;
            case R.id.id_tab_bottom_work:
                setTabSelection(1);
                break;
            case R.id.id_tab_bottom_contact:
                setTabSelection(2);
                break;
            case R.id.id_tab_bottom_mine:
                setTabSelection(3);
                break;
        }
    }

    private void setTabSelection(int index){
        //reset btn
        resetBtn();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);

        switch (index){
            case 0:
                //消息
                btnMsg.setImageResource(R.mipmap.tab_icon_msg_selected);
                if (tabMsgFragment == null)
                {
                    tabMsgFragment = new TabMsgFragment();
                    transaction.add(R.id.id_content, tabMsgFragment);
                } else
                {
                    transaction.show(tabMsgFragment);
                }
                break;
            case 1:
                //工作
                btnWork.setImageResource(R.mipmap.tab_icon_work_selected);
                if (tabWorkFragment == null)
                {
                    tabWorkFragment = new TabWorkFragment();
                    transaction.add(R.id.id_content, tabWorkFragment);
                } else
                {
                    transaction.show(tabWorkFragment);
                }
                break;
            case 2:
                //联系人
                btnContact.setImageResource(R.mipmap.tab_icon_contact_selected);
                if (tabContactFragment == null)
                {
                    tabContactFragment = new TabContactFragment();
                    transaction.add(R.id.id_content, tabContactFragment);
                } else
                {
                    transaction.show(tabContactFragment);
                }
                break;
            case 3:
                btnMine.setImageResource(R.mipmap.tab_icon_personal_selected);
                if (tabMineFragment == null)
                {
                    tabMineFragment = new TabMineFragment();
                    transaction.add(R.id.id_content, tabMineFragment);
                } else
                {
                    transaction.show(tabMineFragment);
                }
                //
                break;
        }

        transaction.commit();
    }

    private void resetBtn(){
        btnMsg.setImageResource(R.mipmap.tab_icon_msg_normal);
        btnWork.setImageResource(R.mipmap.tab_icon_work_normal);
        btnContact.setImageResource(R.mipmap.tab_icon_contact_normal);
        btnContact.setImageResource(R.mipmap.tab_icon_personal_normal);

    }


    private void hideFragments(FragmentTransaction transaction){
        if (tabMsgFragment != null){
            transaction.hide(tabMsgFragment);
        }

        if (tabWorkFragment != null){
            transaction.hide(tabWorkFragment);
        }

        if (tabContactFragment != null){
            transaction.hide(tabContactFragment);
        }

        if (tabMineFragment != null){
            transaction.hide(tabMineFragment);
        }
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
        finish();
    }


    private OfflineMessageService offlineMessageService;

    private void initService() {
        Intent intent2 = new Intent(this, OfflineMessageService.class);
        bindService(intent2, conn, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            // 可以在这里调用service方法
            OfflineMessageService.MyBinder binder = (OfflineMessageService.MyBinder) arg1;
            offlineMessageService = binder.getService();
        }
    };

}