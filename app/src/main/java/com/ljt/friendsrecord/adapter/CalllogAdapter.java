package com.ljt.friendsrecord.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljt.friendsrecord.bean.MyCallLog;
import com.ljt.friendsrecord.biz.ContactBiz;
import com.ljt.friendsrecord.ui.R;
import com.ljt.friendsrecord.view.CircleImageView;


public class CalllogAdapter extends BaseAdapter{
	
	Context context;
	List<MyCallLog> myCallLogs;
	LayoutInflater inflater;
	ContactBiz biz;
	public CalllogAdapter(Context context, List<MyCallLog> myCallLogs) {
		super();
		this.context = context;
		this.myCallLogs = myCallLogs;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		biz = new ContactBiz(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myCallLogs.size();
	}

	@Override
	public MyCallLog getItem(int position) {
		// TODO Auto-generated method stub
		return myCallLogs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.calllog_item_layout,parent,false);
			vh = new ViewHolder();
			vh.ivAvatar = (CircleImageView) convertView.findViewById(R.id.iv_calllog_avatar);
			vh.ivWarning = (ImageView) convertView.findViewById(R.id.iv_calllog_warning);
			vh.ivType = (ImageView) convertView.findViewById(R.id.iv_calllog_type);
			vh.tvPhone = (TextView) convertView.findViewById(R.id.tv_calllog_phone);
			vh.tvName = (TextView) convertView.findViewById(R.id.tv_calllog_name);
			vh.tvDate = (TextView) convertView.findViewById(R.id.tv_calllog_calllogDate);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		MyCallLog mcl = getItem(position);
		vh.ivAvatar.setCircleImageBitmap(biz.getAvatar(mcl.getPhoto_id()));
		String name = mcl.getName();
		if(name.equals("未知号码")){
			vh.ivWarning.setVisibility(View.VISIBLE);
		}else{
			vh.ivWarning.setVisibility(View.GONE);
		}
		vh.tvName.setText(mcl.getName());
		vh.tvPhone.setText(mcl.getPhone());
		vh.tvDate.setText(mcl.getCallLogTime());
		int type = mcl.getType();
		if(type == 2){
			vh.ivType.setVisibility(View.VISIBLE);
		}else{
			vh.ivType.setVisibility(View.GONE);
		}
		if(type == 3){
			vh.tvName.setTextColor(Color.RED);
		}else{
			vh.tvName.setTextColor(Color.BLACK);
		}
		return convertView;
	}
	private class ViewHolder{
		CircleImageView ivAvatar;
		ImageView ivWarning,ivType;
		TextView tvName,tvPhone,tvDate;
	}
	
	public void addAll(List<MyCallLog> list){
		myCallLogs.clear();
		myCallLogs.addAll(list);
		notifyDataSetChanged();
	}
	public void remove(MyCallLog mcl){
		myCallLogs.remove(mcl);
		notifyDataSetChanged();
	}

}
