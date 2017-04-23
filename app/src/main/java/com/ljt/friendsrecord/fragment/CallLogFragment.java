package com.ljt.friendsrecord.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ljt.friendsrecord.adapter.NBCallogAdapter;
import com.ljt.friendsrecord.bean.MyCallLog;
import com.ljt.friendsrecord.biz.ContactBiz;
import com.ljt.friendsrecord.ui.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallLogFragment extends BaseFragment {


    private ContactBiz biz;
    private LinearLayout headerView;
    private ArrayList<MyCallLog> callLogs;
    private NBCallogAdapter adapter;
    private ListView lvView;

    public CallLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);
        biz = new ContactBiz(getActivity());
        initView(view);
        return view;
    }
    private void initView(View view) {
        headerView = (LinearLayout) view.findViewById(R.id.header);
        setHeaderTitle("通话记录");
        lvView = (ListView) view.findViewById(R.id.lv_calllog);
        callLogs = new ArrayList<MyCallLog>();
        adapter = new NBCallogAdapter(getActivity(), callLogs);
        lvView.setAdapter(adapter);
        lvView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MyCallLog mcl = adapter.getItem(position);

                return false;
            }
        });

    }

}
