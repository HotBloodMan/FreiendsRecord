package com.ljt.friendsrecord.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ljt.friendsrecord.adapter.CalllogAdapter;
import com.ljt.friendsrecord.adapter.NBCallogAdapter;
import com.ljt.friendsrecord.bean.MyCallLog;
import com.ljt.friendsrecord.biz.ContactBiz;
import com.ljt.friendsrecord.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallLogFragment extends BaseFragment {

    public static String TAG=CallLogFragment.class.getSimpleName();
    private ContactBiz biz;
    private LinearLayout headerView;
    //数据源
    private List<MyCallLog> callLogs;
    private NBCallogAdapter adapter;
//    private CalllogAdapter adapters;
    private ListView lvView;
    private TextView tvTitleCalllog;

    public CallLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"cccc ----->>>onCreateView CallLog");
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);
        biz = new ContactBiz(getActivity());
        initView(view);
        return view;
    }
    private void initView(View view) {
        headerView = (LinearLayout) view.findViewById(R.id.header_callLog);
        Log.d(TAG,"cccc headerView--->> "+headerView);
//        setHeaderTitle("通话记录");
        tvTitleCalllog = (TextView) headerView.findViewById(R.id.tv_header_title);
        tvTitleCalllog.setText("通话记录");
        lvView = (ListView) view.findViewById(R.id.lv_calllog);

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
    //为数据源赋值
    private void refresh() {
        //利用业务类来完成数据的查询
        callLogs = new ArrayList<MyCallLog>();
        callLogs = biz.getAllCallLogs();
        Log.d(TAG,"cccc callLog list "+callLogs.size());
        adapter = new NBCallogAdapter(getActivity(), callLogs);
//        adapters = new CalllogAdapter(getActivity(), callLogs);
        lvView.setAdapter(adapter);
        lvView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MyCallLog mcl = adapter.getItem(position);
                biz.removeCalllog(adapter,mcl);
                return true;
            }
        });


        //利用适配器，来刷新数据源
        adapter.addAll(callLogs);
    }
}
