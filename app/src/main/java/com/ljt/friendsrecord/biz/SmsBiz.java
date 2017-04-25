package com.ljt.friendsrecord.biz;

import android.content.Context;
import android.graphics.Bitmap;

import com.ljt.friendsrecord.bean.Conversation;
import com.ljt.friendsrecord.utils.SmsUtil;

import java.util.List;

/**
 * Created by 1 on 2017/4/25.
 */

public class SmsBiz {

    Context context;

    public SmsBiz(Context context){
        super();
        this.context=context;
    }

    //
    public Bitmap getAvatar(int photo_id){
        return SmsUtil.getAvatar(context,photo_id);
    }
    /**
     * 获取手机中所有短信的会话记录
     *
     * @return 手机中所有短信会话记录列表
     */
    public List<Conversation> getAllConversations() {
        return SmsUtil.getAllConversations(context);
    }

}
