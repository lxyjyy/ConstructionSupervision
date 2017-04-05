package com.csc.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.csc.bean.ChatCatalog;
import com.csc.bean.ChatMsg;
import com.csc.bean.ChatMsgState;
import com.csc.bean.OFMessage;
import com.csc.bean.User;
import com.csc.db.Database;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.packet.DelayInfo;

/**
 *
 */

public enum UICommon {
    INSTANCE;

    public static User user = null;

    public static String currentChatid = "";

    //发消息
    public void doMessage(Context context, Message message, Database database){
        OFMessage ofMessage = new OFMessage();
        if (message.getType() == Message.Type.chat) {
            UICommon.INSTANCE.doMessage3(message, ofMessage);
        }else if(message.getType() == Message.Type.groupchat){
            UICommon.INSTANCE.doMessage4(message, ofMessage);
        }

        //
//        if (UICommon.user != null && UICommon.user.getUid().equalsIgnoreCase(ofMessage.getFromid())) {//如果是本人发的消息，则不处理
//            return;
//        }

        SharedPreferencesUtils utils = new SharedPreferencesUtils(context);

        if(ofMessage.getMsgtype() != null && ofMessage.getMsgtype().length() > 0) {

            ChatMsg chatMsg = (ChatMsg) JsonObjectTools.mapToObject(ofMessage.getBody(),ChatMsg.class);
            ChatCatalog chatCatalog = new ChatCatalog();
            int unReadNum = 0;

            if (ofMessage.getType() == Message.Type.chat) {
              //设置聊天的类型
                chatMsg.setType(0);

            }

            chatMsg.setFromId(ofMessage.getFromid());
            chatMsg.setToId(ofMessage.getToid());
            chatMsg.setTime(ofMessage.getTime());
            chatMsg.setPacketId(message.getPacketID());
            chatMsg.setMsgType(ofMessage.getMsgtype());
            chatMsg.setFromName(ofMessage.getfromname());
            chatMsg.setFromIcon(ofMessage.getfromicon());


            if (chatMsg.getUrl() != null && chatMsg.getUrl().length() > 0) {
                chatMsg.setUrl(chatMsg.getUrl().replaceAll("\\\\", ""));
            }
            chatMsg.setState(ChatMsgState.DOWN_SUCCESS);
            if (UICommon.currentChatid.equalsIgnoreCase(ofMessage.getChatid())) {
                //是不是当前回话的人
                chatMsg.setIsRead(1);
            }else {
                chatMsg.setIsRead(0);
//				unReadNum = database.getChatUnReadNum(ofMessage.getChatid()) + 1;
            }

            int id = database.addChat(ofMessage.getFromid(), chatMsg);

            //查询 当前该聊天未读消息数

            if (id != -1) {

                if (UICommon.currentChatid.equalsIgnoreCase(ofMessage.getChatid())) {
                    //是不是当前回话的人
//					chatMsg.setIsRead(1);
                    unReadNum = 0;
                }else {
//					chatMsg.setIsRead(0);
                    unReadNum = database.getChatUnReadNum(ofMessage.getChatid()) + 1;
                }

                //消息列表目录
                chatCatalog.setChatId(ofMessage.getChatid());
//				chatCatalog.setHeadpicpath(ofMessage.getfromicon());
                chatCatalog.setMessageId(id);
//				chatCatalog.setName(ofMessage.getfromname());
                chatCatalog.setUnReadNum(unReadNum);
                chatCatalog.setChatMsg(chatMsg);
                chatCatalog.setTime(chatMsg.getTime());
                database.addChatCatalog(chatCatalog);

//                //如果是广播内容则通知
//                if (ChatMsgType.BROADCAST_IMAGE.equalsIgnoreCase(ofMessage.getMsgtype())
//                        || ChatMsgType.BROADCAST_SOUND.equalsIgnoreCase(ofMessage.getMsgtype())
//                        || ChatMsgType.BROADCAST_TEXT.equalsIgnoreCase(ofMessage.getMsgtype())) {
//                    //通知消息界面，暂不开发
//                    //通知消息列表更新数据
//                    Intent intent = new Intent();
//                    intent.setAction(ActionConfig.CHATCATALOG);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("chatCatalog", chatCatalog);
//                    intent.putExtras(bundle);
//                    context.sendBroadcast(intent);
//
//                    //通知聊天界面
//                    if (UiCommon.currentChatid.equalsIgnoreCase(ofMessage.getChatid())) {
//                        //是不是当前回话的人
//                        Intent intent2 = new Intent();
//                        Bundle bundle2 = new Bundle();
//                        if (ofMessage.getType() == Message.Type.chat) {
//                            intent2.setAction(ActionConfig.CHAT);
//                        }
//                        bundle2.putSerializable("chatMsg", chatMsg);
//                        intent2.putExtras(bundle2);
//                        context.sendBroadcast(intent2);
//                    }
//                }else {//如果是广播内容则通知 通知列表变化
//                    Intent intent = new Intent();
//                    intent.setAction(ActionConfig.CHATCATALOG);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("chatCatalog", chatCatalog);
//                    intent.putExtras(bundle);
//                    context.sendBroadcast(intent);
//
//                    //提醒我的刷新 来消息
//					/*Intent intent1 = new Intent();
//					intent1.setAction("com.gdf4p.dc.fragmentm");
//					context.sendBroadcast(intent1);*/
//
//                    //通知聊天界面
//                    if (UiCommon.currentChatid.equalsIgnoreCase(ofMessage.getChatid())) {
//                        //是不是当前回话的人
//                        Intent intent2 = new Intent();
//                        Bundle bundle2 = new Bundle();
//                        if (ofMessage.getType() == Message.Type.chat) {
//                            intent2.setAction(ActionConfig.CHAT);
//                        }
//                        bundle2.putSerializable("chatMsg", chatMsg);
//                        intent2.putExtras(bundle2);
//                        context.sendBroadcast(intent2);
//                    }
//                }

                //更新导航栏未读消息数
//                Intent intent3 = new Intent();
//                intent3.setAction(ActionConfig.UNREADNUM);
//                context.sendBroadcast(intent3);
            }
        }
    }

    public void doMessage3(Message message,OFMessage ofMessage){
        String fromid = message.getFrom().substring(0, message.getFrom().indexOf("@"));
        String toid = message.getTo().substring(0, message.getTo().indexOf("@"));
        ofMessage.setFromid(fromid);
        ofMessage.setToid(toid);
        ofMessage.setType(message.getType());
        ofMessage.setfromicon((String) message.getProperty("fromicon"));
        ofMessage.setfromname((String) message.getProperty("fromname"));
        ofMessage.setMsgtype((String) message.getProperty("msgtype"));
        String body = message.getBody().replace("\n", "").replace("\t", "").replace(" ", "");
        ofMessage.setBody(body);

//        if (ChatMsgType.BROADCAST_IMAGE.equalsIgnoreCase(ofMessage.getMsgtype())
//                || ChatMsgType.BROADCAST_SOUND.equalsIgnoreCase(ofMessage.getMsgtype())
//                || ChatMsgType.BROADCAST_TEXT.equalsIgnoreCase(ofMessage.getMsgtype())) {
//            //消息界面，以accountid 作为chatid
//            ofMessage.setChatid(fromid);
//        }else {
//            //咨询 以questionid 作为chatid
//            ofMessage.setChatid((String) message.getProperty("questionid"));
//        }



        long ts;// 消息时间戳
        DelayInfo timestamp = (DelayInfo) message.getExtension(
                "delay", "urn:xmpp:delay");
        if (timestamp == null)
            timestamp = (DelayInfo) message.getExtension("x",
                    "jabber:x:delay");
        if (timestamp != null)
            ts = timestamp.getStamp().getTime();
        else
            ts = System.currentTimeMillis();
        ofMessage.setTime(ts);
    }

    public void doMessage4(Message message,OFMessage ofMessage){
        String fromid = message.getFrom().substring(message.getFrom().lastIndexOf('/') + 1);
        String toid = "";
//        if (UICommon.user != null && !fromid.equalsIgnoreCase(UiCommon.user.getUid())) {
//            //如果不是本人发的
//            toid = message.getTo().substring(0, message.getTo().indexOf("@"));
//        }
        ofMessage.setFromid(fromid);
        ofMessage.setToid(toid);
        ofMessage.setNaturalname((String) message.getProperty("naturalname"));
        ofMessage.setChatid(message.getFrom().substring(0, message.getFrom().indexOf("@")));
        ofMessage.setType(message.getType());
        ofMessage.setfromicon((String) message.getProperty("fromicon"));
        ofMessage.setfromname((String) message.getProperty("fromname"));
        ofMessage.setMsgtype((String) message.getProperty("msgtype"));
        String body = message.getBody().replace("\n", "").replace("\t", "").replace(" ", "");
        ofMessage.setBody(body);
    }

}
