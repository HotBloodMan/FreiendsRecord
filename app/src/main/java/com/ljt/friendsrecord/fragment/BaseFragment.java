package com.ljt.friendsrecord.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
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


    public void setHeaderTitle(String title){
        if(headerView==null){
            return;
        }
        TextView tv= (TextView) headerView.findViewById(R.id.tv_header_title);
        if(!TextUtils.isEmpty(title)){
            tv.setText(title);
        }else{
            tv.setText("TITLE");
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
