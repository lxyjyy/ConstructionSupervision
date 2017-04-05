package com.csc.listener;

import android.content.Intent;
import android.util.Log;

import org.jivesoftware.smack.ConnectionListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 连接监听类 
 * @author 150410
 * 2015-8-3
 */
public class TaxiConnectionListener implements ConnectionListener {
    private Timer tExit;
    private String username;
    private String password;
    private int logintime = 2000;
    private SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(UiCommon.INSTANCE.getCurrActivity());
  
    @Override
    public void connectionClosed() {  
        Log.i("TaxiConnectionListener", "連接關閉");
        // 關閉連接
        XmppConnectionUtil.getInstance().closeConnection();  
        // 重连服务器  
        tExit = new Timer();
        tExit.schedule(new timetask(), logintime);
    }  
  
    @Override
    public void connectionClosedOnError(Exception e) {
        Log.i("TaxiConnectionListener", "連接關閉異常");
        // 判斷為帳號已被登錄  
        boolean error = e.getMessage().equals("stream:error (conflict)");  
        if (!error) {  
            // 關閉連接  
        	XmppConnectionUtil.getInstance().closeConnection();  
            // 重连服务器  
            tExit = new Timer();
            tExit.schedule(new timetask(), logintime);  
        }  
    }  
  
    class timetask extends TimerTask {
        @Override
        public void run() {  
            if (UiCommon.user != null) {  
                Log.i("TaxiConnectionListener", "嘗試登錄");
                // 连接服务器  
                if (XmppConnectionUtil.getInstance().login(UiCommon.user.getUid(), UiCommon.user.getToken())) {  
                    Log.i("TaxiConnectionListener", "登錄成功");
                    Intent intent = new Intent();
					intent.setAction(ActionConfig.CONNECTION);
					UiCommon.INSTANCE.getCurrActivity().sendBroadcast(intent);
					
                } else {  
                    Log.i("TaxiConnectionListener", "重新登錄");
                    tExit.schedule(new timetask(), logintime);  
                }  
            }  
        }  
    }  
  
    @Override
    public void reconnectingIn(int arg0) {  
    }  
  
    @Override
    public void reconnectionFailed(Exception arg0) {
    }  
  
    @Override
    public void reconnectionSuccessful() {  
    }  
  
}  
