package com.ljt.friendsrecord.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        container = (RelativeLayout) findViewById(R.id.rl_splash_container);
        //container添加动画
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        container.startAnimation(anim);
        //为动画添加监听器
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                //界面切换动画
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
