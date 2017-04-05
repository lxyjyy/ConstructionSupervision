package com.csc.mvp.contract;

import com.lxyjyy.cs.mvp.BaseView;
import com.lxyjyy.cs.mvp.IModel;

/**
 * Created by lxyjyy on 17/3/20.
 */

public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseView {

    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {

    }
}