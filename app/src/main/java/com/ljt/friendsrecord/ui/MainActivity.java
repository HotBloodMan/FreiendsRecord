package com.ljt.friendsrecord.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.ljt.friendsrecord.adapter.MyViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private MyViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1,false);
        //让viewPager多缓存几个fragment
        viewPager.setOffscreenPageLimit(4);
        setListener();
    }
    private void setListener() {
        //viewPager添加滑动监听器
        viewPager.setOnPageChangeListener(this);

        //RadioGroup添加选择监听器
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.calllog:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.contact:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.sms:
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.dialpad:
                        viewPager.setCurrentItem(3,false);
                        break;
                }
            }
        });
    }

//pagerchangeListener-- --- -- ------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                radioGroup.check(R.id.calllog);
                break;
            case 1:
                radioGroup.check(R.id.contact);
                break;
            case 2:
                radioGroup.check(R.id.sms);
                break;
            case 3:
                radioGroup.check(R.id.dialpad);
                break;
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
