package tetviet.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

public class MyLog {

	public static final String TAG = "LoveSMS";
	public static boolean isDebuggable = false;

	public static void init(final Context context) {
		isDebuggable = (0 != (context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));
	}

	public static void d(final String tag, final String message) {
		if (!isDebuggable)
			return;
		Log.d(tag, message);
	}

	public static void d(final String message) {
		if (!isDebuggable)
			return;
		if (message != null && message.length() > 0)
			Log.d(TAG, message);
	}

}
