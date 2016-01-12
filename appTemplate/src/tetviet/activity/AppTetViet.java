package tetviet.activity;

import com.parse.Parse;
import com.parse.ParseInstallation;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AppTetViet extends Application {

    public static String ParseAppId = "c95nKO3sYXDeLIQ1BS3EWtlA2wU90nzBqNp1Qhek";
    public static String ParseClientKey = "c7qf0gwFZUtGX3SK8zdwlEnMIBXXhpH7v0eh6UJ6";
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
