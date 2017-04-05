package com.csc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {
	public final static String SELECTDATE="cs_phone";
	public final static String PHONE = "phone";
	
	public SharedPreferences sharedpreferences;
	
	public SharedPreferencesUtils(Context mContext){
		sharedpreferences = mContext.getSharedPreferences(SELECTDATE, 0);
	}

	
	public String getPhone(){
		return sharedpreferences.getString(PHONE, "");
	}
	
	public void setPhone(String phone){
		Editor editor = sharedpreferences.edit();
		editor.putString(PHONE, phone);
		editor.commit();
	}
	
}
