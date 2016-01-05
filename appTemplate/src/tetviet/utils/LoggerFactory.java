package tetviet.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

public class LoggerFactory {

	public static String TAG = "TinTin";
	public static boolean isDebuggable = true;

	public static void init(final Context context) {
		isDebuggable = (0 != (context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));
		TAG = context.getApplicationInfo().packageName;
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

	public static void e(final String tag, final String message) {
		if (!isDebuggable)
			return;
		Log.e(tag, message);
	}

	public static void e(final String message) {
		if (!isDebuggable)
			return;
		if (message != null && message.length() > 0)
			Log.e(TAG, message);
	}
	public static void error(final String message) {
		if (!isDebuggable)
			return;
		if (message != null && message.length() > 0)
			Log.e(TAG, message);
	}

	public static void i(final String tag, final String message) {
		if (!isDebuggable)
			return;
		Log.i(tag, message);
	}

	public static void i(final String message) {
		if (!isDebuggable)
			return;
		if (message != null && message.length() > 0)
			Log.i(TAG, message);
	}
	
	public static void info(final String message) {
		if (!isDebuggable)
			return;
		if (message != null && message.length() > 0)
			Log.i(TAG, message);
	}
	public static void warn(final String message) {
		if (!isDebuggable)
			return;
		if (message != null && message.length() > 0)
			Log.w(TAG, message);
	}
}
