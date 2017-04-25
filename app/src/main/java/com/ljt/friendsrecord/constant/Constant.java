

package com.ljt.friendsrecord.constant;

import android.net.Uri;

public class Constant {
	public static final String PEOPLE_URI = "content://contacts/people/";
	/**
	 */
	public static final Uri CONVERSATION_URI= Uri.parse("content://mms-sms/conversations");
	/**
	 */
	public static final String CONVERSATION_THREAD_ID= "thread_id";
	/**
	 */
	public static final String CONVERSATION_BODY= "body";
	/**
	 */
	public static final String CONVERSATION_READ= "read";
	/**
	 */
	public static final String CONVERSATION_DATE= "date";
	/**
	 */
	public static final String CONVERSATION_ADDRESS= "address";
	/**
	 */
	public static final Uri SMS_URI = Uri.parse("content://sms");
	/**
	 */
	public static final String SMS_ID = "_id";
	/**
	 */
	public static final String SENT_SHORT_MESSAGE = "com.tarena.SENT_SHORT_MESSAGE";
	/**
	 */
	public static final Uri SMS_SENT_URI = Uri.parse("content://sms/sent");
	/**
	 */
	public static final Uri SMS_INBOX_URI = Uri.parse("content://sms/inbox");
	/**
	 */
	public static final String SMS_RECEIVED_BROADCAST="android.provider.Telephony.SMS_RECEIVED";
}
