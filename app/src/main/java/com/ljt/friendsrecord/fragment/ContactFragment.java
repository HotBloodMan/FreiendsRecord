package com.ljt.friendsrecord.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljt.friendsrecord.adapter.ContactAdapter;
import com.ljt.friendsrecord.bean.Contact;
import com.ljt.friendsrecord.biz.ContactBiz;
import com.ljt.friendsrecord.ui.R;
import com.ljt.friendsrecord.utils.ContactUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment {


    private ContactBiz biz;
    private LinearLayout headerView;
    private TextView tvTitle;
    private GridView gridView;
    private List<Contact> contacts;
    private ContactAdapter adapter;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        biz = new ContactBiz(getActivity());
        initView(view);
        return view;
    }

    private void initView(View view) {
        headerView = (LinearLayout) view.findViewById(R.id.header);
        tvTitle = (TextView) view.findViewById(R.id.tv_header_title);
        tvTitle.setText("联系人");
        setHeaderImage(R.drawable.ic_search,POSITION.RIGHT);
        gridView = (GridView) view.findViewById(R.id.gv_contact);
        //创建适配器
        contacts = new ArrayList<>();
        adapter = new ContactAdapter(getActivity(), contacts);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    //显示详情页
                    Contact contact = adapter.getItem(position);
                    biz.showDetail(contact);
                }else{

                }
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
        List<Contact> list = biz.getAllContacts();
        ContactUtil.sort(list);
        //利用适配器，来刷新数据源
        adapter.addAll(list);

    }

}
