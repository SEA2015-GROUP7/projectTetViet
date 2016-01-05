package tetviet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MSharedPreferences {
	private static MSharedPreferences mSharedPreferences;
	private SecurePreferences securePreferences;

	private Context context;
	private SharedPreferences mSharepreferences;
	private SharedPreferences.Editor editor;

	public void setContext(Context c) {
		this.context = c;
	}

	public static synchronized MSharedPreferences getInstance(Context c) {
		if(mSharedPreferences == null){
			mSharedPreferences = new MSharedPreferences();
			mSharedPreferences.context = c;
			mSharedPreferences.mSharepreferences = PreferenceManager.getDefaultSharedPreferences(mSharedPreferences.context);
			mSharedPreferences.editor = mSharedPreferences.mSharepreferences.edit();
			//mSharedPreferences.securePreferences = SecurePreferences.getInstance(c, TinTinDAO.getInstance().getSecurePreferenceFile(), TinTinDAO.getInstance().getSecurePreferenceKey(),true);
		}
		return mSharedPreferences;
	}

	public synchronized void clear(){

	}

	public SecurePreferences getSecurePreferences() {
		return securePreferences;
	}

	public synchronized String getString(String KEY, String v) {
		if(null != mSharepreferences)
			return mSharepreferences.getString(KEY, v);
		return v;
	}
	public synchronized Boolean getBoolean(String KEY, Boolean v) {
		if(null != mSharepreferences)
			return mSharepreferences.getBoolean(KEY, v);
		return v;
	}
	public synchronized int getInt(String KEY, int v) {
		if(null != mSharepreferences)
			return mSharepreferences.getInt(KEY, v);
		return v;
	}
	public synchronized Float getFloat(String KEY, Float v){
		if(null != mSharepreferences)
			return mSharepreferences.getFloat(KEY, v);
		return v;
	}
	public synchronized Long getLong(String KEY, long v){
		if(null != mSharepreferences)
			return mSharepreferences.getLong(KEY, v);
		return v;
	}
	
	public synchronized void putString(String KEY, String v) {
		if(null != editor){
			editor.putString(KEY, v);
			editor.commit();
		}
	}
	public synchronized void putBoolean(String KEY, Boolean v) {
		if(null != editor){
			editor.putBoolean(KEY, v);
			editor.commit();
		}
	}
	public synchronized void putInt(String KEY, int v) {
		if(null != editor){
			editor.putInt(KEY, v);
			editor.commit();
		}
	}
	public synchronized void putFloat(String KEY, Float v){
		if(null != editor){
			editor.putFloat(KEY, v);
			editor.commit();
		}
	}
	public synchronized void putLong(String KEY, long v){
		if(null != editor){
			editor.putLong(KEY, v);
			editor.commit();
		}
	}

//    public void putByte(String KEY, byte[] v){
//        if(null != editor){
//            editor.putByte
//            editor.commit();
//        }
//    }

}
