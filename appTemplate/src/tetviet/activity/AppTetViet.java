package tetviet.activity;

import com.parse.Parse;
import com.parse.ParseInstallation;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AppTetViet extends Application {

    public static String ParseAppId = "UuYrK2ppCtqlfwyMlQ8eZT98JSgZ3D1qcZgZS7KF";
    public static String ParseClientKey = "hhnOmVDFKfynaXbaOhPqa37TOmwM6drCFCOoQQDw";
    public static final String fontPath = "Roboto-Light.ttf";
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			Parse.initialize(this, ParseAppId, ParseClientKey);
			ParseInstallation.getCurrentInstallation().saveInBackground();

//			ParseFacebookUtils.initialize(getApplicationContext());

//			 TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", fontPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
