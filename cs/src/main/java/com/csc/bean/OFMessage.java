package com.csc.bean;

import org.jivesoftware.smack.packet.Message;

/**
 * Created by lxyjyy on 17/3/31.
 */

public class OFMessage {

    private String fromid;
    private String toid;
    private String fromname;
    private String fromicon;
    private String naturalname;//群组昵称
    private String msgtype;
    private String body;
    private Message.Type type;
    private String chatid;
    private long time;


    public String getFromid() {
        return fromid;
    }
    public void setFromid(String fromid) {
        this.fromid = fromid;
    }
    public String getToid() {
        return toid;
    }
    public void setToid(String toid) {
        this.toid = toid;
    }
    public String getfromname() {
        return fromname;
    }
    public void setfromname(String fromname) {
        this.fromname = fromname;
    }
    public String getfromicon() {
        return fromicon;
    }
    public void setfromicon(String fromicon) {
        this.fromicon = fromicon;
    }
    public String getMsgtype() {
        return msgtype;
    }
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public Message.Type getType() {
        return type;
    }
    public void setType(Message.Type type) {
        this.type = type;
    }
    public String getChatid() {
        return chatid;
    }
    public void setChatid(String chatid) {
        this.chatid = chatid;
    }
    public String getNaturalname() {
        return naturalname;
    }
    public void setNaturalname(String naturalname) {
        this.naturalname = naturalname;
    }
    public String getFromname() {
        return fromname;
    }
    public void setFromname(String fromname) {
        this.fromname = fromname;
    }
    public String getFromicon() {
        return fromicon;
    }
    public void setFromicon(String fromicon) {
        this.fromicon = fromicon;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
}
