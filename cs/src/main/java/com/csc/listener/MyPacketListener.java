package com.csc.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.csc.bean.ChatMsg;
import com.csc.bean.ChatMsgState;
import com.csc.db.Database;
import com.csc.db.DatabaseImpl;
import com.csc.utils.SharedPreferencesUtils;
import com.csc.utils.UICommon;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 监听来自一个特定用户的新的消息  
 * @author 150410
 * 2015-8-3
 */
public class MyPacketListener implements PacketListener {

	private Database database;
	private Context context;
	private SharedPreferencesUtils utils;

	public MyPacketListener(Context context){
		this.context = context;
		utils = new SharedPreferencesUtils(context);
		database = new DatabaseImpl(context);
	}

	@Override
	public void processPacket(Packet arg0) { // 在这里用收到的packet做些什么。
		if (arg0 instanceof Message) {
			Message message = (Message) arg0;
			Log.e("message", message.toXML());
			Iterator<PacketExtension> paIterator = message.getExtensions().iterator();
			while (paIterator.hasNext()) {
				PacketExtension packetExtension = paIterator.next();
				if (packetExtension.getElementName().equalsIgnoreCase("received")) {
					try {
						ByteArrayInputStream tInputStringStream = null;
						tInputStringStream = new ByteArrayInputStream(packetExtension.toXML().getBytes());
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //取得DocumentBuilderFactory实例
						DocumentBuilder builder = factory.newDocumentBuilder(); //从factory获取DocumentBuilder实例
						Document document = builder.parse(tInputStringStream);
						NodeList items = document.getElementsByTagName("received");
						for (int i = 0; i < items.getLength(); i++) {
							Element node = (Element) items.item(i);
							if (node.getAttribute("id") != null ) {
								Log.e("packetid", "packetid = " + node.getAttribute("id"));
								//更新数据库
								String chatID = message.getFrom().substring(0, message.getFrom().indexOf("@"));
								ChatMsg chatMsg = new ChatMsg();
								chatMsg.setPacketId(node.getAttribute("id"));
								chatMsg.setState(ChatMsgState.UPLOAD_SUCCESS);
								database.updateChat(chatID, chatMsg);
								//如果在聊天页面 则通知它更新
//								if (UiCommon.INSTANCE.getCurrActivity() instanceof ConsultActivity) {
//									Intent intent = new Intent();
//									intent.setAction(ActionConfig.CHATRECEIPT);
//									Bundle bundle = new Bundle();
//									bundle.putString("packetid", node.getAttribute("id"));
//									bundle.putString("chatid", chatID);
//									intent.putExtras(bundle);
//									context.sendBroadcast(intent);
//								}
							}
						}
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

			if (!TextUtils.isEmpty(message.getTime())) {
//				utils.setLAST_GROUPMSG_TIME(message.getTime());
			}

			UICommon.INSTANCE.doMessage(context, message, database);

//
		}
	}
}
