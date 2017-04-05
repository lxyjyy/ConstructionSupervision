package com.csc.db;


import android.content.ContentValues;
import android.database.Cursor;

import com.csc.bean.ChatCatalog;


public class ChatCatalogBuilder extends DataBaseBuilder<ChatCatalog> {

	@Override
	public ChatCatalog build(Cursor c) {
		// TODO Auto-generated method stub
		ChatCatalog chatCatalog = new ChatCatalog();
		chatCatalog.setChatId(c.getString(c.getColumnIndex("chatid")));
//		chatCatalog.setHeadpicpath(c.getString(c.getColumnIndex("headpicpath")));
		chatCatalog.setMessageId(c.getInt(c.getColumnIndex("messageid")));
//		chatCatalog.setName(c.getString(c.getColumnIndex("name")));
		chatCatalog.setUnReadNum(c.getInt(c.getColumnIndex("unreadnum")));
		chatCatalog.setType(c.getInt(c.getColumnIndex("type")));
		chatCatalog.setTime(c.getLong(c.getColumnIndex("time")));
		return chatCatalog;
	}

	@Override
	public ContentValues deconstruct(ChatCatalog t) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("chatid", t.getChatId());
//		values.put("headpicpath", t.getHeadpicpath());
		values.put("messageid", t.getMessageId());
//		values.put("name", t.getName());
//		values.put("packetid", t.getPacketid());
		values.put("unreadnum", t.getUnReadNum());
		values.put("time", t.getTime());
		if (t.getType() != -1) {
			values.put("type", t.getType());
		}
		return values;
	}

}
