package tetviet.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/** 
 * FileUtils 
 * @author Nilesh Patel
 */
public class FileUtils {
//	static boolean isInited = false;
	public static final String TypePng = ".png";
	public static final String TypeJpg = ".jpg";
	public static final String TypeBitmap = ".bitamp";
	public static  String TypeFrame = ".";
	public static  String TypeFrameThumb = ".";
	public static  String httpSouce = "";
	public static  String http = "http://";
	
	public static String PrefixNamePhoto = "TinTin_";
	
	/** Used to tag logs */
	@SuppressWarnings("unused")
	private static final String TAG = FileUtils.class.getSimpleName();
	
	public static byte[] readFileToByteArray(File file) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024 * 4];
			int n = 0;
			while (-1 != (n = inputStream.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return output.toByteArray();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// Do nothing
			}
		}
	}
	public static String getRamdomString(){
		return String.valueOf(Calendar.getInstance().getTimeInMillis());
	}
	
	static {
		TypeFrame = ".fr";
		TypeFrameThumb = ".frth";
		httpSouce = "/";
	}
}
