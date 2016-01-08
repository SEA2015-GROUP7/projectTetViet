package tetviet.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

public class Logger {
	private static final String LOG_TAG_BASE = "com.thanhpn.";
//	private final org.apache.log4j.Logger log = Logger.getLogger(ExampleLog4J.class);

    public void myMethod() {
       // log.info("This message should be seen in log file and logcat");
    }

	public static void log(int priority, String tag, String string) {
		if (true) {
			if (tag == null || tag.length() == 0) {
				tag = LOG_TAG_BASE;
			} else if (!tag.startsWith(LOG_TAG_BASE)) {
				tag = LOG_TAG_BASE + tag;
			}

			switch (priority) {
			case Log.INFO:
				Log.i(tag, string);
				break;
			case Log.DEBUG:
				Log.d(tag, string);
				break;
			case Log.ERROR:
				Log.e(tag, string);
				//appendLog(tag + "---" + string);
				break;
			case Log.VERBOSE:
				Log.v(tag, string);
				break;
			case Log.WARN:
				Log.w(tag, string);
				break;

			default:
				Log.println(priority, tag, string);
				break;
			}

			
			// return;
		} else if (true) {
			if (tag == null || tag.length() == 0) {
				tag = LOG_TAG_BASE;
			} else if (!tag.startsWith(LOG_TAG_BASE)) {
				tag = LOG_TAG_BASE + tag;
			}

			switch (priority) {
			case Log.INFO:
				Log.i(tag, string);
				break;
			case Log.DEBUG:
				Log.d(tag, string);
				break;
			case Log.ERROR:
				Log.e(tag, string);
				break;
			case Log.VERBOSE:
				Log.v(tag, string);
				break;
			case Log.WARN:
				Log.w(tag, string);
				break;

			default:
				Log.println(priority, tag, string);
				break;
			}
			//appendLog(tag + "---" + string);
		}
	}

	static SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
	static SimpleDateFormat tf = new SimpleDateFormat("hh-mm-ss");

//	public static void appendLog(String text) {
//		if (FileManager.getAvailableSDCardSpace() > 10) {
//			Calendar c = Calendar.getInstance();
//
//			String formattedDate = df.format(c.getTime());
//			
//			text = tf.format(c.getTime()) + text; 
//			File logFile = new File(Constant.CACHE_DIR+"/log " + formattedDate + ".file");
//			if (!logFile.exists()) {
//				try {
//					logFile.createNewFile();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			try {
//				// BufferedWriter for performance, true to set append to file
//				// flag
//				BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
//						true));
//				buf.append(text + "\n");
//				buf.newLine();
//				buf.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

}
