package com.ljt.friendsrecord.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljt.friendsrecord.ui.R;


/**
 * Created by 1 on 2017/4/23.
 */

public class BaseFragment extends Fragment {

    public enum  POSITION{LEFT,RIGHT};
    LinearLayout headerView;

    public static String TAG=BaseFragment.class.getSimpleName();
    public void setHeaderTitle(String title){
        Log.d(TAG,"cccc-->> setHeaderTitle ");
        if(headerView!=null){
            Log.d(TAG,"cccc-->> setHeaderTitle 1111");
            TextView tv = (TextView) headerView.findViewById(R.id.tv_header_title);
            if (!TextUtils.isEmpty(title)) {
                Log.d(TAG, "cccc-->> setHeaderTitle title=" + title);
                tv.setText(title);
            } else {
                Log.d(TAG, "cccc-->> setHeaderTitle title=null ");
                tv.setText("你好CallLog");
            }
        }else {
            return;
        }
    }
    public void setHeaderImage(int resId,POSITION position){
        if(headerView==null){
            return;
        }
        ImageView imageView=null;
        switch (position){
            case LEFT:
                imageView= (ImageView) headerView.findViewById(R.id.iv_header_left);
                break;
            case RIGHT:
                imageView= (ImageView) headerView.findViewById(R.id.iv_header_right);
                break;
        }
        imageView.setImageResource(resId);
    }

}
