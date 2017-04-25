package com.ljt.friendsrecord.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljt.friendsrecord.adapter.NBCallogAdapter;
import com.ljt.friendsrecord.bean.MyCallLog;
import com.ljt.friendsrecord.biz.ContactBiz;
import com.ljt.friendsrecord.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialFragment extends BaseFragment {
    ListView lvView;
    //适配器
    //	CalllogAdapter adapter;
    NBCallogAdapter adapter;
    //数据源
    List<MyCallLog> calllogs;
    ContactBiz biz;
    private TextView title;

    public DialFragment() {
        // Required empty public constructor
    }
//---------------------------------------

    TextView tvTitle;//header里面的标题栏
    ImageView ivRight;//header里面的删除按钮
    RelativeLayout dialPad;//拨号键盘
    ImageButton ibCall;//拨号键(绿色)

    SoundPool pool;//用来播放音效



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dial, container, false);
        biz=new ContactBiz(getActivity());

        pool=new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1.0f,1.0f,0,0,1.0f);
            }
        });
        initView(view);
        return view;
    }
    private  void initView(View view){
        headerView= (LinearLayout) view.findViewById(R.id.header);
//        view.findViewById(R.id.tv_header_title);
        setHeaderTitle("拨打电话");
        setHeaderImage(R.drawable.ic_add_icon,POSITION.LEFT);
        setHeaderImage(R.drawable.ic_backspace,POSITION.RIGHT);

        tvTitle = (TextView) headerView.findViewById(R.id.tv_header_title);
        tvTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //只能触摸
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if(content.length()>13){
                    s.delete(13,content.length());
                }
            }
        });
        ivRight= (ImageView) headerView.findViewById(R.id.iv_header_right);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=tvTitle.getText().toString();
                if(number.equals("拨打电话")){
                    return;
                }else if(number.length()==1){
                    number="拨打电话";
                }else if(number.length()==5||number.length()==10){
                    number=number.substring(0,number.length()-2);
                }else{
                    number=number.substring(0,number.length()-1);
                }
                tvTitle.setText(number);
            }
        });
        dialPad= (RelativeLayout) view.findViewById(R.id.rl_dial_container);
        ibCall= (ImageButton) dialPad.findViewById(R.id.ib_dial);
        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.a);
                String number = tvTitle.getText().toString();
                if(TextUtils.isEmpty(number)||number.equals("拨打电话")){
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });
        initDialPad(dialPad);
        lvView= (ListView) view.findViewById(R.id.lv_dial);
        //创建适配器
        calllogs=new ArrayList<>();
        adapter=new NBCallogAdapter(getActivity(),calllogs);
        lvView.setAdapter(adapter);
        lvView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MyCallLog mcl = adapter.getItem(position);
                biz.removeCalllog(adapter,mcl);
                return true;
            }
        });
    }

    private void initDialPad(RelativeLayout dialPad) {
        //获得dialPad的宽度。它的宽度是屏幕的宽度
        int width = getActivity().
                getResources().
                getDisplayMetrics().
                widthPixels;
        //获得dialPad的高度，300dp，但是要将dp转为像素
        int height = (int) TypedValue.
                applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        300,
                        getActivity().getResources().getDisplayMetrics());
        //获得一个拨号键（TextView）的宽度和高度
        int tvWidth = width/3;
        int tvHeight = height/5;
        //在dialPad上面画12个TextView
        for(int i=0;i<12;i++){
            final TextView tv = new TextView(getActivity());
            //设置tv属性的代码
            tv.setText(i+1+"");
            if(i==9){
                tv.setText("*");
            }
            if(i==10){
                tv.setText("0");
            }
            if(i==11){
                tv.setText("#");
            }
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            tv.setTextColor(Color.BLACK);
            //内容在TextView中居中
            tv.setGravity(Gravity.CENTER);
            //父控件的布局参数
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(
                            tvWidth, tvHeight);
            //设置每一个TextView的位置
            tv.setId(i+1);//设置id值，但是id不能为0
            //添加谁在谁右侧的规则
            if(i%3!=0){
                params.addRule(RelativeLayout.RIGHT_OF, i);
            }
            //添加谁在谁下方的规则
            if(i>=3){
                params.addRule(RelativeLayout.BELOW, i-2);
            }
            tv.setLayoutParams(params);
            //添加一些简单的拨号效果
            tv.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //TextView t = (TextView)v;
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            playSound(R.raw.b);
                            tv.setTextColor(Color.BLUE);
                            //修改tvTitle里面的内容
                            String number = tvTitle.getText().toString();
                            if(number.equals("拨打电话")){
                                number = tv.getText().toString();
                            }else if(number.length()==3||number.length()==8){
                                number = number+"-"+tv.getText().toString();
                            }
                            else{
                                number = number + tv.getText().toString();
                            }

                            tvTitle.setText(number);

                            break;
                        case MotionEvent.ACTION_UP:
                            tv.setTextColor(Color.BLACK);
                            break;

                    }
                    return true;
                }
            });
            dialPad.addView(tv);
        }
    }
    //播放音效的方法
    public void playSound(int resId){
        pool.load(getActivity(),resId,1);
    }

}
