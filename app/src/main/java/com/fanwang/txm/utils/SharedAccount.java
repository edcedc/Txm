package com.fanwang.txm.utils;

import android.content.Context;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;


public class SharedAccount {

	private static SharedPreferencesTool share;

	private SharedAccount() {
	}

	private static SharedAccount instance = null;
	private final String MOBILE_KEY = "mobile";
	private final String PASSWORD_KEY = "password";
	private final String LOCK_KEY = "lock";
	private final String SESSIONID = "sessionId";

	public static SharedAccount getInstance(Context context) {
		if (instance == null) {
			instance = new SharedAccount();
		}
		share = SharedPreferencesTool.getInstance(context, "account");
		return instance;
	}

	public boolean isHaveAccount() {
		if (!StringUtils.isEmpty(getMobile())
				&& !StringUtils.isEmpty(getPassword())
				&& !StringUtils.isEmpty(getLock())) {
			return true;
		}
		return false;
	}


	public void save(String mobile, String password) {
		share.putString(MOBILE_KEY, mobile);
		share.putString(PASSWORD_KEY, password);
		share.commit();
	}

	public void saveSessionId(String sessionId){
		share.putString(SESSIONID, sessionId);
		share.commit();
	}

	public void save(String lock) {
		try {
			lock =  EncryptUtils.encryptMD5File2String(lock);
		} catch (Exception e) {
			e.printStackTrace();
		}
		share.putString(LOCK_KEY, lock);
		share.commit();
	}

	public void delete() {
//		share.remove(MOBILE_KEY);
		share.remove(PASSWORD_KEY);
		share.remove(LOCK_KEY);
		share.remove(SESSIONID);
		share.commit();
	}

	public void deletePassWord(){
		share.remove(PASSWORD_KEY);
		share.commit();
	}


	public String getMobile() {
		return share.getString(MOBILE_KEY, "");
	}

	public String getLock() {
		return share.getString(LOCK_KEY, "");
	}

	public String getPassword() {
		return share.getString(PASSWORD_KEY, "");
	}

	public String getSessionId(){
		return share.getString(SESSIONID, "");
	}

}
