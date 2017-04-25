package com.ljt.friendsrecord.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.LruCache;

import com.ljt.friendsrecord.bean.Contact;
import com.ljt.friendsrecord.bean.MyCallLog;
import com.ljt.friendsrecord.ui.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by 1 on 2017/4/23.
 */

public class ContactUtil {
    private static int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
    //添加头像图形的缓存
    public static LruCache<Integer, Bitmap> bitmapCache =
            new LruCache<Integer, Bitmap>(maxSize);


    public static List<Contact> getAllContacts(Context context) {
        List<Contact> list = new ArrayList<Contact>();
        //利用系统的ContentProvider来查询联系人信息
        //数据来自contacts,data表
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.PHOTO_ID};
        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, null);
        //遍历从contacts数据表中取出的数据表
        while (c.moveToNext()) {
            Contact contact = new Contact();
            int id = c.getInt(0);
            contact.set_id(id);
            contact.setPhoto_id(c.getInt(1));
            //利用联系人的_id，到data数据表中继续查询
            ContentResolver cr2 = context.getContentResolver();
            Cursor c2 = cr2.query(ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Data.MIMETYPE, ContactsContract.Data.DATA1}
                    , ContactsContract.Data.RAW_CONTACT_ID + " = ?",
                    new String[]{String.valueOf(id)}, null);
            //从data表中取回的，特定id联系人的具体数据集
            while (c2.moveToNext()) {
                String mimeType = c2.getString(0);
                if (mimeType.equals("vnd.android.cursor.item/email_v2")) {
                    contact.setEmail(c2.getString(1));
                }
                if (mimeType.equals("vnd.android.cursor.item/name")) {
                    contact.setName(c2.getString(1));
                }
                if (mimeType.equals("vnd.android.cursor.item/phone_v2")) {
                    contact.setPhone(c2.getString(1));
                }
                if (mimeType.equals("vnd.android.cursor.item/postal-address_v2")) {
                    contact.setAddress(c2.getString(1));
                }
            }
            c2.close();
            //对于只有号码没有姓名的联系人做一个处理
            if (TextUtils.isEmpty(contact.getName())) {
                contact.setName(contact.getPhone());
            }
            list.add(contact);
        }
        c.close();
        return list;
    }

    //获取联系人头像
    public static Bitmap getAvatar(Context context, int photo_id) {
        Bitmap avatar = null;
        avatar = bitmapCache.get(photo_id);
        if (avatar == null) {
            if (photo_id == 0) {
                avatar = BitmapFactory.decodeResource(context.getResources()
                        , R.drawable.ic_contact);
            } else {
                //进入data数据表，查询头像的真正数据
                ContentResolver cr = context.getContentResolver();
                Cursor c = cr.query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.Data.DATA15},
                        ContactsContract.Data._ID + " = ?",
                        new String[]{String.valueOf(photo_id)}, null);
                c.moveToNext();
                //Data15中存储的是头像对应的二进制数据
                byte[] bytes = c.getBlob(0);
                c.close();
                avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
            bitmapCache.put(photo_id, avatar);
        }
        return avatar;
    }

    public static void sort(List<Contact> list) {
        Collections.sort(list, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public static List<MyCallLog> getAllCallLogs(Context context) {
        ArrayList<MyCallLog> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String[] projection = {
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                "photo_id",
                "name"
        };
        String sortOrder = CallLog.Calls.DATE + " desc";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        Cursor c = cr.query(CallLog.Calls.CONTENT_URI, projection, null, null, sortOrder);
        while(c.moveToNext()){
            MyCallLog mcl = new MyCallLog();
            mcl.set_id(c.getInt(0));
            mcl.setPhone(c.getString(1));
            mcl.setType(c.getInt(2));
            mcl.setDate(c.getLong(3));

            mcl.setPhoto_id(getPhotoIdByNumber(context,mcl.getPhone()));

            String name = c.getString(5);
            if(TextUtils.isEmpty(name)){
                name="未知号码";
            }
            mcl.setName(name);
            //将date属性的值转换
            String callLogTime=getCalllogTime(mcl.getDate());
            mcl.setCallLogTime(callLogTime);
            list.add(mcl);
        }
        c.close();
        return list;
    }

    public static String getCalllogTime(long stample){
        String result="";
        long now = System.currentTimeMillis();
        long gap = now - stample;
        if(gap<=24*60*60*1000){
            result=new SimpleDateFormat("HH:mm").format(new Date(stample));
        }else if(gap<=2*24*60*60*1000){
            result = "昨天";
        }else if(gap<=7*24*60*60*1000){
            //拿星期几
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(stample);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            switch (weekDay) {
                case 1:
                    result = "星期日";
                    break;
                case 2:
                    result = "星期一";
                    break;
                case 3:
                    result = "星期二";
                    break;
                case 4:
                    result = "星期三";
                    break;
                case 5:
                    result = "星期四";
                    break;
                case 6:
                    result = "星期五";
                    break;
                case 7:
                    result = "星期六";
                    break;
            }
        }else{
            result = new SimpleDateFormat("yyyy-MM-dd").format(new Date(stample));
        }
        return  result;
    }

    public static void removeCalllog(Context context, MyCallLog mcl) {
        ContentResolver cr = context.getContentResolver();
        String where = CallLog.Calls._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(mcl.get_id())};
        //利用ContentProvider删除数据表中的数据
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cr.delete(CallLog.Calls.CONTENT_URI,
                where,
                selectionArgs);
    }

    //根据给定的的电话来反查电话号码对应的联系人的photo_id

    public static int getPhotoIdByNumber(Context context,String number){
        int photo_id=0;
        ContentResolver cr = context.getContentResolver();
        //content://contacts/phonelookup/13513513500
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, number);

        String[] projection = {ContactsContract.PhoneLookup.PHOTO_ID};
        Cursor c = cr.query(uri, projection, null, null, null);
        if(c.moveToNext()){
           photo_id= c.getInt(0);
        }
        c.close();
        return  photo_id;
    }

}
