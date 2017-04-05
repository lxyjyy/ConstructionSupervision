package com.csc.db;

import android.content.ContentValues;
import android.database.Cursor;


import com.csc.bean.ChatMsg;

import java.io.IOException;

public class ChatMsgBuilder extends DataBaseBuilder<ChatMsg>{

	@Override
	public ChatMsg build(Cursor c) {
		// TODO Auto-generated method stub
		ChatMsg obj = new ChatMsg();
		obj.setFromId(c.getString(c.getColumnIndex("fromid")));
		obj.setToId(c.getString(c.getColumnIndex("toid")));
		obj.setContext(c.getString(c.getColumnIndex("context")));
		obj.setType(c.getInt(c.getColumnIndex("type")));
		obj.setState(c.getInt(c.getColumnIndex("state")));
		obj.setIsRead(c.getInt(c.getColumnIndex("isRead")));
		obj.setPacketId(c.getString(c.getColumnIndex("packetid")));
		obj.setTime(c.getLong(c.getColumnIndex("time")));
		obj.setUrl(c.getString(c.getColumnIndex("url")));
		obj.setMsgType(c.getString(c.getColumnIndex("msgtype")));
		obj.setDuration(c.getString(c.getColumnIndex("duration")));
		obj.setSize(c.getString(c.getColumnIndex("size")));
		obj.setLocalUrl(c.getString(c.getColumnIndex("localUrl")));
		obj.setProgress(c.getInt(c.getColumnIndex("progress")));
		obj.setFromId(c.getString(c.getColumnIndex("fromicon")));
		obj.setFromName(c.getString(c.getColumnIndex("fromname")));

		return obj;
	}

	@Override
	public ContentValues deconstruct(ChatMsg obj) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("fromid", obj.getFromId());
		values.put("toid", obj.getToId());
		values.put("context", obj.getContext());
		values.put("type", obj.getType());
		values.put("state", obj.getState());
		values.put("isRead", obj.getIsRead());
		values.put("url", obj.getUrl());
		values.put("packetid", obj.getPacketId());
		values.put("time", obj.getTime());
		values.put("msgtype", obj.getMsgType());
		values.put("duration", obj.getDuration());
		values.put("size", obj.getSize());
		values.put("localUrl", obj.getLocalUrl());
		values.put("progress", obj.getProgress());
		values.put("fromicon", obj.getFromIcon());
		values.put("fromname", obj.getFromName());

		return values;
	}

}
