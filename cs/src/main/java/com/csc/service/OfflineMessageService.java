   package com.csc.service;

import java.util.Iterator;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.OfflineMessageManager;
import org.jivesoftware.smackx.bytestreams.ibb.packet.Data;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.csc.common.WEApplication;
import com.csc.db.Database;
import com.csc.db.DatabaseImpl;
import com.csc.utils.UICommon;
import com.csc.utils.XmppConnectionUtil;

import com.lxyjyy.cs.base.AppManager;
import com.lxyjyy.cs.base.BaseApplication;
import com.lxyjyy.cs.base.BaseService;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

   /**
	* 离线消息服务
	*/
   public class OfflineMessageService extends Service {

       @Override
       public IBinder onBind(Intent arg0) {

           getOfflineMessages();
           return myBinder;
       }

	   public void getOfflineMessages(){
			Observable.create(new Observable.OnSubscribe<Object>() {
				@Override
				public void call(Subscriber<? super Object> subscriber) {

				XmppConnectionUtil xmppConnectionUtil = XmppConnectionUtil.getInstance();
               XMPPConnection connection = xmppConnectionUtil.getConnection();

               if (connection != null && !connection.isAuthenticated()) {
				   //openfire 用户名:手机号 密码:token
                   while (!xmppConnectionUtil.login(UICommon.user.getMobile(), UICommon.user.getToken())) {
                       try {
                           Thread.sleep(10000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }

               try {
                   if (connection != null && connection.isAuthenticated()) {

                       OfflineMessageManager omm = new OfflineMessageManager(connection);
                       // 创建一个对象
                       Iterator<Message> ommlist = omm.getMessages();
                       // 获取消息，结果为一个Message迭代器
                       // 处理离线消息
                       if (ommlist != null) {
						   WEApplication WEApp = (WEApplication) (WEApplication.getContext());
                           Activity activity = WEApp.getAppManager().getCurrentActivity();

                           Database database = new DatabaseImpl(activity);
                           while (ommlist.hasNext()) {
                               Message message = ommlist.next();
                               UICommon.INSTANCE.doMessage(activity, message,database);
                           }
                       }
                       String to = "";
                       if (connection.isAuthenticated()) {
                           omm.deleteMessages();
   //						Presence presence = new Presence(Presence.Type.available);
   //						presence.setTo(to);
   //						SharedPreferencesUtils utils = new SharedPreferencesUtils(OfflineMessageService.this);
   //						XtalkMessage packetExtension = new XtalkMessage("time", "xxxx",utils.getLAST_GROUPMSG_TIME());
   ////						packetExtension.setValue("time", "" + System.currentTimeMillis());
   //						presence.addExtension(packetExtension);
   //						connection.sendPacket(presence);

                           Presence presence2 = new Presence(
                                   Presence.Type.available);
                           connection.sendPacket(presence2);
                       }
                   }
               } catch (XMPPException e) {
                   e.printStackTrace();
               }
				}
			}).observeOn(Schedulers.io());
       }

       @Override
       public int onStartCommand(Intent intent, int flags, int startId) {
           return super.onStartCommand(intent, flags, startId);
       }

       @Override
       public void onDestroy() {
           super.onDestroy();

       }

       public class MyBinder extends Binder {

           public OfflineMessageService getService() {
               return OfflineMessageService.this;
           }
       }
       private MyBinder myBinder = new MyBinder();

   }
