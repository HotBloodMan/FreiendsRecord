package com.ljt.friendsrecord.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 1 on 2017/4/23.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter{
    Context mContext;
    LayoutInflater inflater;
    List<T> list;

    public MyBaseAdapter(Context context,List<T> list){
        super();
        this.mContext=context;
        this.list=list;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position,convertView,parent);
    }
    public abstract  View getItemView(int position,View convertView,ViewGroup parent);

    public void addAll(List<T> l){
        list.clear();
        list.addAll(l);
        notifyDataSetChanged();
    }
    public void remove(T t){
        list.remove(t);
        notifyDataSetChanged();
    }
    public void add(T t){
        list.add(t);
        notifyDataSetChanged();
    }

}
