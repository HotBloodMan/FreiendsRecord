package com.ljt.friendsrecord.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.ljt.friendsrecord.bean.Conversation;
import com.ljt.friendsrecord.constant.Constant;

import java.util.ArrayList;
import java.util.List;

import static com.ljt.friendsrecord.utils.ContactUtil.getPhotoIdByNumber;

/**
 * Created by 1 on 2017/4/25.
 */

public class SmsUtil {


    /**
     * 获取手机中所有短信的会话记录
     *
     * @param context 上下文
     * @return 手机中所有短信会话记录列表
     */
    public static List<Conversation> getAllConversations(Context context) {
        List<Conversation> list = new ArrayList<Conversation>();
        ContentResolver cr = context.getContentResolver();
        //要取出的列名都定义在了Constant常量类中，方便使用
        String[] projection = new String[]{
                Constant.CONVERSATION_THREAD_ID,
                Constant.CONVERSATION_BODY,
                Constant.CONVERSATION_ADDRESS,
                Constant.CONVERSATION_DATE,
                Constant.CONVERSATION_READ
        };
        //按短信会话最后一条短信发生的时间进行倒排序
        String sortOrder = Constant.CONVERSATION_DATE + " DESC";
        //获得数据源
        Cursor c = cr.query(Constant.CONVERSATION_URI, projection, null, null, sortOrder );
        while(c.moveToNext()){
            Conversation conversation = new Conversation();
            conversation.setThread_id(c.getInt(0));
            conversation.setBody(c.getString(1));
            conversation.setPhone(c.getString(2));
            conversation.setDate(c.getLong(3));
            conversation.setRead(c.getInt(4));
            //以下conversation的3个属性不能通过数据源直接获得，要再经过相应的处理
            conversation.setPhoto_id(getPhotoIdByNumber(context, conversation.getPhone()));
//            conversation.setName(getNameByNumber(context, conversation.getPhone()));
//            conversation.setConversationDate(getConversationDate(conversation.getDate()));

            list.add(conversation);
        }
        c.close();
        return list;
    }


    public static Bitmap getAvatar(Context context, int photo_id){
        return ContactUtil.getAvatar(context,photo_id);
    }
}
