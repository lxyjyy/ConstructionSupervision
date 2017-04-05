package com.csc.bean;

import java.io.Serializable;

/**
 * 消息体 消息列表中显示
 <message from="1@1.com" to="2@2.com" xml:lang="zh-cn" type="chat"  questionid="1" msgtype="1">
 <body>{"context":"111111"}</body>
 </message>
 */

public class ChatMsg implements Serializable{

    //数据库索引
    private int id;
    //发来消息的用户ID
    private String fromId;
    //发来消息的用户名称
    private String fromName;
    //发来消息的用户头像
    private String fromIcon;
    //接收消息的ID
    private String toId;
    //聊天类型 0:单聊(chat) 1:群聊(groupchat)
    private int type;
    //聊天内容
    private String context;
    //消息类型 0代表文字,1代表语音
    private String msgType;
    //
    private String size;
    //语音,图片下载地址
    private String url;
    //
    private String duration;


    //状态:1.上传成功 ,2.上传失败 3.下载成功,4.下载失败 0.正在上传
    private int state;
    //是否阅读过 0.未读 1.已读
    private int isRead;
    //包的ID唯一值
    private String packetId;
    //本地地址
    private String localUrl;
    //时间
    private long time;
    //0~100
    private int progress;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromIcon() {
        return fromIcon;
    }

    public void setFromIcon(String fromIcon) {
        this.fromIcon = fromIcon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
