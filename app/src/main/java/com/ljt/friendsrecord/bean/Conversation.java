package com.ljt.friendsrecord.bean;
/**
 *
 */
public class Conversation {

	private int thread_id;
	private long date;
	private String conversationDate;
	private String body;
	private int read;
	private int photo_id;
	private String name;
	private String phone;
	
	public int getThread_id() {
		return thread_id;
	}
	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getConversationDate() {
		return conversationDate;
	}
	public void setConversationDate(String conversationDate) {
		this.conversationDate = conversationDate;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Conversation [thread_id=" + thread_id + ", date=" + date + ", conversationDate=" + conversationDate
				+ ", body=" + body + ", read=" + read + ", photo_id=" + photo_id + ", name=" + name + ", phone=" + phone
				+ "]";
	}
	
	



}
