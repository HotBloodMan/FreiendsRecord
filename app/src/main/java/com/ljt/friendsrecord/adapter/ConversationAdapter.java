package com.ljt.friendsrecord.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljt.friendsrecord.bean.Conversation;
import com.ljt.friendsrecord.biz.SmsBiz;
import com.ljt.friendsrecord.ui.R;
import com.ljt.friendsrecord.view.CircleImageView;

import java.util.List;

/**
 * Created by 1 on 2017/4/25.
 */

public class ConversationAdapter extends MyBaseAdapter<Conversation>{
    private static final int UNREAD = 0;

    private SmsBiz biz;

    public ConversationAdapter(Context context, List<Conversation> list) {
        super(context, list);
        biz = new SmsBiz(context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.conversation_item_layout, parent,false);
            vh = new ViewHolder();

            vh.ivAvatar = (CircleImageView) convertView.findViewById(R.id.iv_conversation_avatar);
            vh.ivWarning = (ImageView) convertView.findViewById(R.id.iv_conversation_warning);
            vh.ivRead = (ImageView) convertView.findViewById(R.id.iv_conversation_read);
            vh.tvBody = (TextView) convertView.findViewById(R.id.tv_conversation_body);
            vh.tvDate = (TextView) convertView.findViewById(R.id.tv_coversation_conversationDate);
            vh.tvName = (TextView) convertView.findViewById(R.id.tv_conversation_name);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        Conversation conversation = getItem(position);
        //设置圆形头像
        vh.ivAvatar.setCircleImageBitmap(biz.getAvatar(conversation.getPhoto_id()));
        //如果会话中的姓名为空，则使用会话的电话号码来充当vh.tvName的值,并且显示红色叹号
        if(TextUtils.isEmpty(conversation.getName())){
            vh.tvName.setText(conversation.getPhone());
            vh.ivWarning.setVisibility(View.VISIBLE);
        }else{
            vh.tvName.setText(conversation.getName());
            vh.ivWarning.setVisibility(View.GONE);
        }
        //如果会话的最后一条短信为未读，则vh.tvName的颜色为红色，并且显示绿色小圆点提示
        if(conversation.getRead() == UNREAD){
            vh.tvName.setTextColor(Color.RED);
            vh.ivRead.setVisibility(View.VISIBLE);
        }else{
            vh.tvName.setTextColor(Color.BLACK);
            vh.ivRead.setVisibility(View.GONE);
        }
        //显示会话中最后一条短信内容的正文
        vh.tvBody.setText(conversation.getBody());
        //显示会话中最后一条短信发生时间，时间格式为美工设计的格式
        vh.tvDate.setText(conversation.getConversationDate());

        return convertView;
    }
    private class ViewHolder{
        CircleImageView ivAvatar;
        ImageView ivRead,ivWarning;
        TextView tvName,tvBody,tvDate;
    }
}
