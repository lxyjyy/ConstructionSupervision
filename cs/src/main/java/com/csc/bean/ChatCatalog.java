package com.csc.bean;

import java.io.Serializable;

/**
 * 消息列表 添加消息体ChatMsg
 */

public class ChatCatalog implements Serializable,Comparable<ChatCatalog>{
    //发送方的ID
    private String chatId;
    //聊天用户的昵称
    private String name;
    //聊天用户的头像
    private String headPicPath;
    //数据库对应的消息序列号
    private int messageId;
    //未读消息数目
    private int unReadNum;
    //消息内容
    private ChatMsg chatMsg;
    //消息发来的时间
    private long time;
    //消息类型 0:普通消息 1:聊天室 2:
    private int type;
    //聊天置顶
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPicPath() {
        return headPicPath;
    }

    public void setHeadPicPath(String headPicPath) {
        this.headPicPath = headPicPath;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int compareTo(ChatCatalog o) {
        return (int)(o.getTime() - time);
    }
}
