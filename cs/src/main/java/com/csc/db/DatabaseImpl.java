package com.csc.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ContextThemeWrapper;

import com.csc.bean.ChatCatalog;
import com.csc.bean.ChatMsg;
import com.csc.bean.ChatMsgType;
import com.csc.bean.User;
import com.csc.utils.SharedPreferencesUtils;

import org.jivesoftware.smack.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/3/29.
 */

public class DatabaseImpl extends SQLiteOpenHelper implements Database{

    private static final String DB_NAME = "cs";
    //用户
    private static final String TABLE_USER = "db_user";
    //聊天内容
    private static final String TABLE_CHAT = "db_chat";
    //聊天目录
    private static final String TABLE_CHAT_ML = "db_chat_ml";

    private static final int version = 1;

    private SharedPreferencesUtils sp;

    public DatabaseImpl(Context activity){
        super(activity, DB_NAME + (new SharedPreferencesUtils((activity)).getPhone()),null,version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_USER
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "uid VARCHAR, name VARCHAR, "
                + "mobile VARCHAR, "
                + "age VARCHAR, "
                + "sex VARCHAR, "
                + "token varchar, deptId varchar, roleId varchar, openfireId varchar, headPicUrl varchar);");

        //聊天目录
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TABLE_CHAT_ML +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "chatid VARCHAR unique, name VARCHAR ," +
                " headpicpath VARCHAR," +
                " messageid INTEGER,unreadnum INTEGER," +
                ",level INTEGER," +
                "type INTEGER,time LONG" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        if (db == null)
            return;

        ContentValues values = new ContentValues();
        values.putAll(new UserDatabaseBuilder().deconstruct(user));

        String[] whereArgs = {"1"};
        int row_count = db.update(TABLE_USER, values, "id=?", whereArgs);

        if (row_count == 0){
            db.insert(TABLE_USER, null, values);
        }

        db.close();
    }

    @Override
    public User getUser() {
        User user = null;
        SQLiteDatabase db = getWritableDatabase();
        if (db == null)
            return user;

        String[] whereArgs = {"1"};

        Cursor query = db.query(TABLE_USER, null, "id = ?", whereArgs, null, null, null);

        if (query != null){
            while (query.moveToNext()){
                user = new UserDatabaseBuilder().build(query);
                break;
            }
        }

        query.close();
        db.close();
        return  user;

    }

    @Override
    public void deleteUser() {
         SQLiteDatabase db = getWritableDatabase();
          if (db == null){
              return;
          }

        String[] whereArgs = { "1" };

        db.delete(TABLE_USER, "id=?", whereArgs);

        db.close();
    }

    @Override
    public int addChat(String chatID, ChatMsg obj) {
        SQLiteDatabase db = getWritableDatabase();
        if (db == null){
            return 0;
        }

        createChat(chatID,db);

        ContentValues values = new ContentValues();
        values.putAll(new ChatMsgBuilder().deconstruct(obj));

        int id = 0;
        db.insert(TABLE_CHAT + chatID, null, values);//更新数据库

        Cursor cursor = db.rawQuery("select last_insert_rowid() newid;", null);
        while (cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex("newid"));
        }

        db.close();
        return id;
    }

    /**
     * 创建一个聊天
     */
    public void createChat(String chatID, SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_CHAT + chatID
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "fromid VARCHAR,toid VARCHAR,context VARCHAR,url VARCHAR,"
                + "type INTEGER,state INTEGER,isRead INTEGER,questionid VARCHAR,"
                + "packetid VARCHAR,msgtype VARCHAR,"
                + "time LONG,duration VARCHAR,size VARCHAR,fromname VARCHAR,fromicon VARCHAR,localUrl VARCHAR,progress INTEGER);");
    }

    @Override
    public List<ChatMsg> getChat(String chatID, int index, int size) {

        List<ChatMsg> list = new ArrayList<ChatMsg>();

        SQLiteDatabase db = getWritableDatabase();
        if (db == null)
            return list;
        createChat(chatID,db);

        //查询咨询聊天内容
        String addition = "' and (msgtype = '" + ChatMsgType.IMAGE +"' or msgtype = '" + ChatMsgType.PLAIN_TEXT +"' or msgtype = '" + ChatMsgType.SPEECH_SOUND + "')";
        Cursor cursor = db.rawQuery("select * from " + TABLE_CHAT+chatID + " where 1 = 1'"  + addition + " order by time desc limit " + size + " offset " + index + ";", null);
        while (cursor.moveToNext()) {
            ChatMsg obj = new ChatMsgBuilder().build(cursor);
            list.add(obj);
        }

        db.close();
        return list;

    }

    @Override
    public void deleteChat(String chatID) {
//
//        SQLiteDatabase db = getWritableDatabase();
//        if (db == null)
//                return;
//        String addition = "msgtype = '" + ChatMsgType.BROADCAST_IMAGE +"' or msgtype = '" + ChatMsgType.BROADCAST_SOUND +"' or msgtype = '" + ChatMsgType.BROADCAST_TEXT +"';";
//        db.execSQL("delete from " + TABLE_CHAT+chatid+" where " + addition);
//        db.execSQL("delete from " + TABLE_CHAT_ML +" where type != 0 and doctorid = " +doctorid +";");
//        db.close();

    }

    @Override
    public void updateChat(String chatID, ChatMsg obj) {

        SQLiteDatabase db = getWritableDatabase();
        if (db == null)
            return;
        //不存在就创建
        createChat(chatID,db);

        ContentValues values = new ContentValues();
        values.put("state", obj.getState());

        if (obj.getLocalUrl() != null && obj.getLocalUrl().length() > 0) {
            values.put("localUrl", obj.getLocalUrl());
        }

        if (obj.getUrl() != null && obj.getUrl().length() > 0) {
            values.put("url", obj.getUrl());
        }

        if (obj.getProgress() != -1) {
            values.put("progress", obj.getProgress());
        }

        db.update(TABLE_CHAT+chatID, values, "packetid=?", new String[]{obj.getPacketId()});

        db.close();

    }

    @Override
    public void addChatCatalog(ChatCatalog catalog) {

        SQLiteDatabase db = getWritableDatabase();
        if (db == null) return;

        ContentValues values = new ContentValues();
        values.putAll(new ChatCatalogBuilder().deconstruct(catalog));

        String[] whereArgs = {catalog.getChatId()};
        int row_count = db.update(TABLE_CHAT_ML, values, "chatid=?", whereArgs);
        if (row_count == 0) {
            db.insert(TABLE_CHAT_ML, null, values);
        }


        db.close();
    }

    @Override
    public int getChatUnReadNum(String chatID) {
        SQLiteDatabase db = getWritableDatabase();
        ChatCatalog chatMsg = null;
        if (db == null) return 0;
        Cursor cursor = db.query(TABLE_CHAT_ML, null, "chatid = ?", new String[]{chatID}, null, null, null);
        while (cursor.moveToNext()) {
            chatMsg = new ChatCatalogBuilder().build(cursor);
        }
        db.close();
        return chatMsg == null ? 0:chatMsg.getUnReadNum();
    }

    @Override
    public int getChatNums(String chatID) {
        return 0;
    }

    @Override
    public void updateUnReadNum(String chatID, int unReadNum) {

    }

    @Override
    public int getChatUnReadNum() {
        return 0;
    }

    @Override
    public List<ChatCatalog> getMessageList() {
        return null;
    }
}
