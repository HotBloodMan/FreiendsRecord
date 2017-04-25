package com.ljt.friendsrecord.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljt.friendsrecord.bean.Contact;
import com.ljt.friendsrecord.biz.ContactBiz;
import com.ljt.friendsrecord.ui.R;
import com.ljt.friendsrecord.view.CircleImageView;


public class ContactAdapter extends BaseAdapter{

	Context context;
	List<Contact> contacts;
	LayoutInflater inflater;
	ContactBiz biz;
	public ContactAdapter(Context context, List<Contact> contacts) {
		super();
		this.context = context;
		this.contacts = contacts;
		inflater = (LayoutInflater) 
				context.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		biz = new ContactBiz(context);
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Contact getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.contact_item_layout, parent,false);
			vh = new ViewHolder();
			vh.tv = (TextView) convertView.findViewById(R.id.tv_contact_item);
			vh.iv = (CircleImageView) convertView.findViewById(R.id.iv_contact_item);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		Contact contact = getItem(position);
		vh.tv.setText(contact.getName());
		//根据postion，来确定加载头像图片
		if(position==0){
			Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_add_contact);
			vh.iv.setImageBitmap(b);
		}else{
			Bitmap bm = biz.
					getAvatar(contact.getPhoto_id());
			vh.iv.setCircleImageBitmap(bm);
		}
		return convertView;
	}

	private class ViewHolder{
		CircleImageView iv;
		TextView tv;
	}
	/**
	 * 刷新数据源
	 * @param list
	 */
	public void addAll(List<Contact> list){
		contacts.clear();
		contacts.addAll(list);
		Contact c = new Contact();
		c.setName("添加联系人");
		contacts.add(0,c);
		notifyDataSetChanged();
	}

}
