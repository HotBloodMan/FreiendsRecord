package com.ljt.friendsrecord.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ljt.friendsrecord.fragment.CallLogFragment;
import com.ljt.friendsrecord.fragment.ContactFragment;
import com.ljt.friendsrecord.fragment.DialFragment;
import com.ljt.friendsrecord.fragment.SmsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/22/022.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter{
    List<Fragment>  fragments;
    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        //初始化fragment
        fragments=new ArrayList<>();
        fragments.add(new CallLogFragment());
        fragments.add(new ContactFragment());
        fragments.add(new SmsFragment());
        fragments.add(new DialFragment());
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }
}
