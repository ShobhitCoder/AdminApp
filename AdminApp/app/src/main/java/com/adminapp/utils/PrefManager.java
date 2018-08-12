package com.adminapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

	public static String KEY_FCM_ID = "fcm_id";
	public static String KEY_USER_ID = "user_id";
	public static String KEY_FIRST_NAME = "first_name";
	public static String KEY_LAST_NAME = "last_name";
	public static String KEY_MOBILE = "mobile";
	public static String KEY_MOBILE_COUNTRY_CODE = "mobile_country_code";
	public static String KEY_TWO_CHAR_COUNTRY_CODE = "two_char_country_code";
	public static String KEY_EMAIL = "email";

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "pexit";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String ADD_DEVICES = "add_devices";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void storeDevice(String isFirstTime) {
        editor.putString(ADD_DEVICES, isFirstTime);
        editor.commit();
    }

    public String getDevices() {
        return pref.getString(ADD_DEVICES,"");
    }

	public void setStringData (String KEY, String VALUE ) {
		editor.putString ( KEY, VALUE );
		editor.commit ();
	}

	public String getStringData (String KEY, String VALUE ) { return pref.getString ( KEY, VALUE ); }

	public void setIntegerData (String KEY, int VALUE ) {
		editor.putInt ( KEY, VALUE );
		editor.commit ();
	}

	public int getIntegerData (String KEY, int VALUE ) { return pref.getInt ( KEY, VALUE ); }

	public void setLongDat (String KEY, long VALUE ) {
		editor.putLong ( KEY, VALUE );
		editor.commit ();
	}

	public long getLongData (String KEY, long VALUE ) { return pref.getLong ( KEY, VALUE ); }

	public void setBooleanData (String KEY, boolean VALUE ) {
		editor.putBoolean ( KEY, VALUE );
		editor.commit ();
	}

	public boolean getBooleanData ( String KEY ) { return pref.getBoolean ( KEY, false ); }

	public void logOut () {
		editor.clear ();
		editor.commit ();
	}

	public void setToken (String KEY, String VALUE ) {
		editor.putString ( KEY, VALUE );
		editor.commit ();
	}

	public String getToken (String KEY, String VALUE ) {
		return pref.getString ( KEY, VALUE ); }


}
