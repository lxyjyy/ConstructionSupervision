package com.lxyjyy.cs.rxerrorhandler.handler;

import android.content.Context;

import com.lxyjyy.cs.rxerrorhandler.handler.listener.ResponseErroListener;


/**
 */
public class ErrorHandlerFactory {
    public final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ResponseErroListener mResponseErroListener;

    public ErrorHandlerFactory(Context mContext, ResponseErroListener mResponseErroListener) {
        this.mResponseErroListener = mResponseErroListener;
        this.mContext = mContext;
    }

    /**
     *  处理错误
     * @param throwable
     */
    public void handleError(Throwable throwable) {
        mResponseErroListener.handleResponseError(mContext, (Exception) throwable);
    }
}
