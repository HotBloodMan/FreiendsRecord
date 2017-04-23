package com.ljt.friendsrecord.bean;

public class MyCallLog {
	private int _id;//_id
	private int photo_id;//头像idͷ
	private long date;///通话时间
	private String name;//姓名(有可能为null)
	private String phone;//电话号码
	private int type;//类型 1:呼入 2：呼出 3：未接
	private String callLogTime;//将date属性的值转化为美工要求的时间格式
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCallLogTime() {
		return callLogTime;
	}
	public void setCallLogTime(String callLogTime) {
		this.callLogTime = callLogTime;
	}
	@Override
	public String toString() {
		return "MyCallLog [_id=" + _id + ", photo_id=" + photo_id + ", date="
				+ date + ", name=" + name + ", phone=" + phone + ", type="
				+ type + ", callLogTime=" + callLogTime + "]";
	}
	
	
}
