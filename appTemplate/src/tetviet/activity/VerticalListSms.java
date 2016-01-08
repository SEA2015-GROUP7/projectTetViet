package tetviet.activity;

import com.sea.tetviet.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class VerticalListSms extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vertical_list_sms);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_vertical_list_sms, menu);
		return true;
	}

}
