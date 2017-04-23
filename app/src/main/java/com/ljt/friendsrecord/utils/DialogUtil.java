package com.ljt.friendsrecord.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.ljt.friendsrecord.adapter.NBCallogAdapter;
import com.ljt.friendsrecord.bean.MyCallLog;
import com.ljt.friendsrecord.ui.R;

/**
 * Created by 1 on 2017/4/23.
 */

public class DialogUtil {



    public static void removeCalllog(final Context context, final NBCallogAdapter adapter, final MyCallLog mcl){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("删除");
        builder.setMessage("兄弟，确认要删除该通话记录吗？");
        builder.setNegativeButton("容我想想",null);
        //2 添加删除按钮的监听器
        builder.setPositiveButton("删了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactUtil.removeCalllog(context,mcl);
                adapter.remove(mcl);
            }
        });
        builder.create().show();
    }
}
