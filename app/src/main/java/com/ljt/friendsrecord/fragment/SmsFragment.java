package com.ljt.friendsrecord.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ljt.friendsrecord.adapter.ConversationAdapter;
import com.ljt.friendsrecord.bean.Conversation;
import com.ljt.friendsrecord.biz.SmsBiz;
import com.ljt.friendsrecord.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends BaseFragment {

    ListView lvView;
    //适配器
    ConversationAdapter adapter;
    //数据源
    List<Conversation> conversations;
    //短信业务类
    SmsBiz biz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sms, null);
        biz = new SmsBiz(getActivity());
        initView(view);
        return view;
    }

    private void initView(View view) {
        //获得头部视图组件，并设置头部标题
        headerView = (LinearLayout) view.findViewById(R.id.header);
        setHeaderTitle("短消息");
        lvView = (ListView) view.findViewById(R.id.lv_conversation);
        //创建适配器
        conversations = new ArrayList<Conversation>();
        adapter = new ConversationAdapter(getActivity(), conversations);
        lvView.setAdapter(adapter);
        //长按删除相应的短信会话记录
        lvView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Conversation conversation = adapter.getItem(position);
//                biz.removeConversation(adapter,conversation);
                return true;
            }
        });
        /**
         * 点击了某一个短信会话，跳转到ChatActivity界面
         */
        lvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Conversation conversation = adapter.getItem(position);
                //更新短息会话的未读状态
//                biz.updateConversationRead(conversation.getThread_id());

//                Intent intent = new Intent(getActivity(),ChatActivity.class);
//                intent.putExtra("thread_id", conversation.getThread_id());
//                intent.putExtra("name", conversation.getName());
//                intent.putExtra("address", conversation.getPhone());
//                getActivity().startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
    /**
     * 为数据源赋值
     */
    private void refresh() {
        //利用业务类来完成数据的查询
        List<Conversation> list = biz.getAllConversations();
        //利用适配器，来刷新数据源
        adapter.addAll(list);
    }

}
