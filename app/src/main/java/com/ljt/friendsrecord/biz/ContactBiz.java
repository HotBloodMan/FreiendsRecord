package com.ljt.friendsrecord.biz;

import android.content.Context;
import android.graphics.Bitmap;

import com.ljt.friendsrecord.adapter.NBCallogAdapter;
import com.ljt.friendsrecord.bean.Contact;
import com.ljt.friendsrecord.bean.MyCallLog;
import com.ljt.friendsrecord.utils.ContactUtil;
import com.ljt.friendsrecord.utils.DialogUtil;

import java.util.List;

/**
 * Created by 1 on 2017/4/23.
 */

public class ContactBiz {

    Context mContext;
    public ContactBiz(Context context){
        super();
        this.mContext=context;
    }
    public List<Contact> getAllContacts(){
        List<Contact> result = ContactUtil.getAllContacts(mContext);
        return result;
    }
    //获取联系人头像
    public Bitmap getAvatar(int photo_id){
        Bitmap avatar = ContactUtil.getAvatar(mContext, photo_id);
        return avatar;
    }
    //显示详情页
    public void showDetail(Contact contact){

    }
//    public List<MyCallLog> getAllCallLogs(){
//        return ContactUtil.getAllCallLogs(mContext);
//    }
    public void removeCalllog(NBCallogAdapter adapter, MyCallLog mcl){
        DialogUtil.removeCalllog(mContext,adapter,mcl);
    }

}
