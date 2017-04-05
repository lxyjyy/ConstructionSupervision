package com.csc.db;

import com.csc.bean.ChatCatalog;
import com.csc.bean.ChatMsg;
import com.csc.bean.User;

import java.util.List;

/**
 * 数据库操作接口
 */

public interface Database {
    //添加用户
    public void addUser(User user);
    //获取用户
    public  User getUser();
    //删除用户
    public void deleteUser();


    /**
     * 增加一条聊天记录
     * @param chatID 会话ID 发送方ID
     * @param obj 消息体
     * @return
     */
    public int addChat(String chatID , ChatMsg obj);

    /**
     * 读取聊天记录
     * @param chatID 会话ID 发送方ID
     * @param index 分页索引
     * @param size 每页对应的聊天记录条数
     * @return
     */
    public List<ChatMsg> getChat(String chatID,int index,int size);

    /**
     * 删除聊天记录
     * @param chatID
     */
    public void deleteChat(String chatID);

    /**
     * 更新聊天状态
     * @param chatID
     * @param obj
     */
    public void updateChat(String chatID,ChatMsg obj);

    /**
     * 添加/更新聊天列表数据库
     * @param catalog
     */
    public void addChatCatalog(ChatCatalog catalog);

    /**
     * 获取聊天未读总数
     * @param chatID
     * @return
     */
    public int getChatUnReadNum(String chatID);

    /**
     * 获取聊天总条数
     * @param chatID
     * @return
     */
    public int getChatNums(String chatID);

    /**
     * 更新未读条数
     * @param chatID
     * @param unReadNum
     */
    public void updateUnReadNum(String chatID,int unReadNum);


    //获取聊天总未读总数
    public int getChatUnReadNum();

    /**
     * 获取聊天列表
     * @return
     */
    public List<ChatCatalog> getMessageList();
}
